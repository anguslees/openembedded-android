DESCRIPTION = "Perl is a popular scripting language."
HOMEPAGE = "http://www.perl.org/"
SECTION = "devel/perl"
LICENSE = "Artistic|GPL"
PRIORITY = "optional"
# We need gnugrep (for -I)
DEPENDS = "virtual/db perl-native grep-native"
PR = "r34"

# Major part of version
PVM = "5.8"

SRC_URI[perl588targz.md5sum] = "b8c118d4360846829beb30b02a6b91a7"
SRC_URI[perl588targz.sha256sum] = "e15d499321e003d12ed183601e37ee7ba5f64b278d1de30149ce01bd4a3f234d"

SRC_URI = "http://ftp.funet.fi/pub/CPAN/src/5.0/perl-5.8.8.tar.gz;name=perl588targz \
        file://Makefile.patch \
        file://Makefile.SH.patch \
        file://makedepend-dash.patch \
        file://installperl.patch \
        file://perl-dynloader.patch \
        file://perl-moreconfig.patch \
        file://letgcc-find-errno.patch \
        file://generate-sh.patch \
        file://perl-5.8.8-gcc-4.2.patch \
        file://09_fix_installperl.patch \
        file://52_debian_extutils_hacks.patch \
        file://53_debian_mod_paths.patch \
        file://54_debian_perldoc-r.patch \
        file://58_debian_cpan_config_path.patch \
        file://60_debian_libnet_config_path.patch \
        file://62_debian_cpan_definstalldirs.patch \
        file://64_debian_enc2xs_inc.patch \
        file://asm-pageh-fix.patch \
        file://shared-ldflags.patch \
        file://config.sh \
        file://config.sh-32 \
        file://config.sh-32-le \
        file://config.sh-32-be \
        file://config.sh-64 \
        file://config.sh-64-le \
        file://config.sh-64-be"

inherit siteinfo

# Where to find the native perl
HOSTPERL = "${STAGING_BINDIR_NATIVE}/perl${PV}"

# Where to find .so files - use the -native versions not those from the target build
export PERLHOSTLIB = "${STAGING_LIBDIR_NATIVE}/perl/${PV}/"

# LDFLAGS for shared libraries
export LDDLFLAGS = "${LDFLAGS} -shared"

do_configure() {
        # Make hostperl in build directory be the native perl
        ln -sf ${HOSTPERL} hostperl

        # Do out work in the cross subdir
        cd Cross

        # Generate configuration
        rm -f config.sh-${TARGET_ARCH}-${TARGET_OS}
        for i in ${WORKDIR}/config.sh \
                 ${WORKDIR}/config.sh-${@siteinfo_get_bits(d)} \
                 ${WORKDIR}/config.sh-${@siteinfo_get_bits(d)}-${@siteinfo_get_endianess(d)}; do
            cat $i >> config.sh-${TARGET_ARCH}-${TARGET_OS}
        done

        # Fixups for uclibc
        if [ "${TARGET_OS}" = "linux-uclibc" -o "${TARGET_OS}" = "linux-uclibceabi" ]; then
                sed -i -e "s,\(d_crypt_r=\)'define',\1'undef',g" \
                       -e "s,\(d_futimes=\)'define',\1'undef',g" \
                       -e "s,\(crypt_r_proto=\)'\w+',\1'0',g" \
                       -e "s,\(d_getnetbyname_r=\)'define',\1'undef',g" \
                       -e "s,\(getnetbyname_r_proto=\)'\w+',\1'0',g" \
                       -e "s,\(d_getnetbyaddr_r=\)'define',\1'undef',g" \
                       -e "s,\(getnetbyaddr_r_proto=\)'\w+',\1'0',g" \
                       -e "s,\(d_getnetent_r=\)'define',\1'undef',g" \
                       -e "s,\(getnetent_r_proto=\)'\w+',\1'0',g" \
                       -e "s,\(d_sockatmark=\)'define',\1'undef',g" \
                       -e "s,\(d_sockatmarkproto=\)'\w+',\1'0',g" \
                    config.sh-${TARGET_ARCH}-${TARGET_OS}
        fi

        # Update some paths in the configuration
        sed -i -e 's,@DESTDIR@,${D},g' \
               -e 's,@ARCH@,${TARGET_ARCH}-${TARGET_OS},g' \
               -e "s%/usr/include/%${STAGING_INCDIR}/%g" \
	       -e 's,/usr/,${exec_prefix}/,g' \
            config.sh-${TARGET_ARCH}-${TARGET_OS}

        if test "${MACHINE}" != "native"; then
            # These are strewn all over the source tree
            for foo in `grep -I -m1 \/usr\/include\/.*\\.h ${WORKDIR}/* -r | cut -f 1 -d ":"` ; do
                echo Fixing: $foo
                sed -e "s%/usr/include/%${STAGING_INCDIR}/%g" -i $foo
            done
        fi

        rm -f config
        echo "ARCH = ${TARGET_ARCH}" > config
        echo "OS = ${TARGET_OS}" >> config
}
do_compile() {
        if test "${MACHINE}" != "native"; then
            sed -i -e 's|/usr/include|${STAGING_INCDIR}|g' ext/Errno/Errno_pm.PL
        fi
        cd Cross
        oe_runmake perl LD="${TARGET_SYS}-gcc"
}
do_install() {
	oe_runmake install
        # Add perl pointing at current version
        ln -sf perl${PV} ${D}${bindir}/perl

        # Fix up versioned directories
        mv ${D}/${libdir}/perl/${PVM} ${D}/${libdir}/perl/${PV}
        mv ${D}/${datadir}/perl/${PVM} ${D}/${datadir}/perl/${PV}
        ln -sf ${PV} ${D}/${libdir}/perl/${PVM}
        ln -sf ${PV} ${D}/${datadir}/perl/${PVM}

        # Remove unwanted file
        rm -f ${D}/${libdir}/perl/${PV}/.packlist

        # Fix up shared library
        mv -f ${D}/${libdir}/perl/${PV}/CORE/libperl.so ${D}/${libdir}/libperl.so.${PV}
        ln -sf libperl.so.${PV} ${D}/${libdir}/libperl.so.5

        # Fix up installed configuration
        if test "${MACHINE}" != "native"; then
            sed -i -e "s,${D},,g" \
                   -e "s,-isystem${STAGING_INCDIR} ,,g" \
                   -e "s,${STAGING_LIBDIR},${libdir},g" \
                   -e "s,${STAGING_BINDIR},${bindir},g" \
                   -e "s,${STAGING_INCDIR},${includedir},g" \
                   -e "s,${CROSS_DIR}${base_bindir}/,,g" \
                ${D}${bindir}/h2xs \
                ${D}${bindir}/h2ph \
                ${D}${datadir}/perl/${PV}/pod/*.pod \
                ${D}${datadir}/perl/${PV}/cacheout.pl \
                ${D}${datadir}/perl/${PV}/FileCache.pm \
                ${D}${libdir}/perl/${PV}/Config.pm \
                ${D}${libdir}/perl/${PV}/Config_heavy.pl \
                ${D}${libdir}/perl/${PV}/CORE/perl.h \
                ${D}${libdir}/perl/${PV}/CORE/pp.h
        fi
}
do_stage() {
        install -d ${STAGING_LIBDIR_NATIVE}/perl/${PV} \
                   ${STAGING_LIBDIR}/perl/${PV}/CORE \
                   ${STAGING_DATADIR}/perl/${PV}/ExtUtils
        # target config, used by cpan.bbclass to extract version information
        install config.sh ${STAGING_LIBDIR}/perl/
        # target configuration, used by native perl when cross-compiling
        install lib/Config_heavy.pl ${STAGING_LIBDIR_NATIVE}/perl/${PV}/Config_heavy-target.pl
	# target configuration
        install lib/Config.pm       ${STAGING_LIBDIR}/perl/${PV}/
	install lib/ExtUtils/typemap ${STAGING_DATADIR}/perl/${PV}/ExtUtils/
        # perl shared library headers
        for i in av.h embed.h gv.h keywords.h op.h perlio.h pp.h regexp.h \
                 uconfig.h XSUB.h cc_runtime.h embedvar.h handy.h opnames.h \
                 perliol.h pp_proto.h regnodes.h unixish.h config.h EXTERN.h \
                 hv.h malloc_ctl.h pad.h perlsdio.h proto.h scope.h utf8.h \
                 cop.h fakesdio.h INTERN.h mg.h patchlevel.h perlsfio.h \
                 reentr.h sv.h utfebcdic.h cv.h fakethr.h intrpvar.h \
                 nostdio.h perlapi.h perlvars.h reentr.inc thrdvar.h util.h \
                 dosish.h form.h iperlsys.h opcode.h perl.h perly.h regcomp.h \
                 thread.h warnings.h; do
            install $i ${STAGING_LIBDIR}/perl/${PV}/CORE
        done
}

PACKAGES = "perl-dbg perl perl-misc perl-lib perl-dev perl-pod perl-doc"
FILES_${PN} = "${bindir}/perl ${bindir}/perl${PV}"
FILES_${PN}-lib = "${libdir}/libperl.so* ${libdir}/perl/${PVM} ${datadir}/perl/${PVM}"
FILES_${PN}-dev = "${libdir}/perl/${PV}/CORE"
FILES_${PN}-pod = "${datadir}/perl/${PV}/pod \
                   ${datadir}/perl/${PV}/*/*.pod \
                   ${datadir}/perl/${PV}/*/*/*.pod \
                   ${libdir}/perl/${PV}/*.pod"
FILES_perl-misc = "${bindir}/*"
FILES_${PN}-dbg += "${libdir}/perl/${PV}/auto/*/.debug \
                    ${libdir}/perl/${PV}/auto/*/*/.debug \
                    ${libdir}/perl/${PV}/auto/*/*/*/.debug \
                    ${datadir}/perl/${PV}/auto/*/.debug \
                    ${datadir}/perl/${PV}/auto/*/*/.debug \
                    ${datadir}/perl/${PV}/auto/*/*/*/.debug \
                    ${libdir}/perl/${PV}/CORE/.debug"
FILES_${PN}-doc = "${datadir}/perl/${PV}/*/*.txt \
                   ${datadir}/perl/${PV}/*/*/*.txt \
                   ${datadir}/perl/${PV}/Net/*.eg \
                   ${datadir}/perl/${PV}/CGI/eg \
                   ${datadir}/perl/${PV}/ExtUtils/PATCHING \
                   ${datadir}/perl/${PV}/ExtUtils/NOTES \
                   ${datadir}/perl/${PV}/ExtUtils/typemap \
                   ${datadir}/perl/${PV}/ExtUtils/MANIFEST.SKIP \
                   ${datadir}/perl/${PV}/CPAN/SIGNATURE \
                   ${datadir}/perl/${PV}/CPAN/PAUSE2003.pub \
                   ${datadir}/perl/${PV}/B/assemble \
                   ${datadir}/perl/${PV}/B/makeliblinks \
                   ${datadir}/perl/${PV}/B/disassemble \
                   ${datadir}/perl/${PV}/B/cc_harness \
                   ${datadir}/perl/${PV}/ExtUtils/xsubpp \
                   ${datadir}/perl/${PV}/Encode/encode.h \
                   ${datadir}/perl/${PV}/unicore/mktables \
                   ${datadir}/perl/${PV}/unicore/mktables.lst \
                   ${datadir}/perl/${PV}/unicore/version"

RPROVIDES_perl-lib = "perl-lib"

# Create a perl-modules package recommending all the other perl
# packages (actually the non modules packages and not created too)
ALLOW_EMPTY_perl-modules = "1"
PACKAGES_append = " perl-modules "
RRECOMMENDS_perl-modules = "${@bb.data.getVar('PACKAGES', d, 1).replace('perl-modules ', '').replace('perl-dbg ', '').replace('perl-misc ', '').replace('perl-dev ', '').replace('perl-pod ', '').replace('perl-doc ', '')}"

python populate_packages_prepend () {
        libdir = bb.data.expand('${libdir}/perl/${PV}', d)
        do_split_packages(d, libdir, 'auto/(.*)(?!\.debug)/', 'perl-module-%s', 'perl module %s', recursive=True, allow_dirs=False, match_path=True)
        do_split_packages(d, libdir, '(.*)\.(pm|pl|e2x)', 'perl-module-%s', 'perl module %s', recursive=True, allow_dirs=False, match_path=True)
        datadir = bb.data.expand('${datadir}/perl/${PV}', d)
        do_split_packages(d, datadir, 'auto/(.*)(?!\.debug)/', 'perl-module-%s', 'perl module %s', recursive=True, allow_dirs=False, match_path=True)
        do_split_packages(d, datadir, '(.*)\.(pm|pl|e2x)', 'perl-module-%s', 'perl module %s', recursive=True, allow_dirs=False, match_path=True)
}

PACKAGES_DYNAMIC = "perl-module-*"

require perl-rdepends_${PV}.inc
require perl-rprovides.inc

PARALLEL_MAKE = ""
