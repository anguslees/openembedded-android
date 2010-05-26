DESCRIPTION = "Perl is a popular scripting language."
HOMEPAGE = "http://www.perl.org/"
SECTION = "libs"
LICENSE = "Artistic|GPL"
DEPENDS = "virtual/db-native gdbm-native"
PR = "r19"
NATIVE_INSTALL_WORKS = "1"

FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/perl-${PV}"

SRC_URI = "http://ftp.funet.fi/pub/CPAN/src/5.0/perl-${PV}.tar.gz \
           file://perl-5.8.8-gcc-4.2.patch \
           file://Configure-multilib.patch \
           file://perl-configpm-switch.patch \
           file://native-nopacklist.patch \
           file://native-no-gdbminc.patch \
           file://native-perlinc.patch \
           file://makedepend-dash.patch \
           file://asm-pageh-fix.patch"

S = "${WORKDIR}/perl-${PV}"

inherit native

do_configure () {
    ./Configure \
        -Dcc="${CC}" \
        -Dcflags="${CFLAGS}" \
        -Dldflags="${LDFLAGS}" \
        -Dcf_by="Open Embedded" \
        -Dprefix=${prefix} \
        -Dvendorprefix=${prefix} \
        -Dvendorprefix=${prefix} \
        -Dsiteprefix=${prefix} \
        \
        -Dprivlib=${STAGING_LIBDIR}/perl/${PV} \
        -Darchlib=${STAGING_LIBDIR}/perl/${PV} \
        -Dvendorlib=${STAGING_LIBDIR}/perl/${PV} \
        -Dvendorarch=${STAGING_LIBDIR}/perl/${PV} \
        -Dsitelib=${STAGING_LIBDIR}/perl/${PV} \
        -Dsitearch=${STAGING_LIBDIR}/perl/${PV} \
        \
        -Duseshrplib \
        -Dusethreads \
        -Duseithreads \
        -Duselargefiles \
	-Dnoextensions=ODBM_File \
        -Ud_dosuid \
        -Ui_db \
        -Ui_ndbm \
        -Ui_gdbm \
        -Di_shadow \
        -Di_syslog \
        -Duseperlio \
        -Dman3ext=3pm \
        -Uafs \
        -Ud_csh \
        -Uusesfio \
        -Uusenm -des
    sed "s!${STAGING_DIR}/bin!${STAGING_BINDIR}!;
         s!${STAGING_DIR}/lib!${STAGING_LIBDIR}!;
	 s!^installbin=.*!installbin=\'${STAGING_BINDIR}\'!;
	 s!^installsitebin=.*!installsitebin=\'${STAGING_BINDIR}\'!" < config.sh > config.sh.new
    mv config.sh.new config.sh
}

do_install() {
	oe_runmake DESTDIR="${D}" install.perl

        # We need a hostperl link for building perl
        ln -sf perl${PV} ${D}${bindir}/hostperl
        # Store native config in non-versioned directory
        install -d ${D}${libdir}/perl/${PV}/CORE \
                   ${D}${datadir}/perl/${PV}/ExtUtils
        install config.sh ${D}${libdir}/perl
	# Fix Errno.pm for target builds
	sed -i -r "s,^\tdie\ (\"Errno\ architecture.+)$,\twarn\ \1," ${D}${libdir}/perl/${PV}/Errno.pm
	# target configuration
        install lib/Config.pm       ${D}${libdir}/perl/${PV}/
	install lib/ExtUtils/typemap ${D}${datadir}/perl/${PV}/ExtUtils/
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
            install $i ${D}${libdir}/perl/${PV}/CORE
        done
}

do_install_append_nylon() {
        # get rid of definitions not supported by the gcc version we use for nylon...
        for i in ${D}${libdir}/perl/${PV}/Config_heavy.pl ${D}${libdir}/perl/config.sh; do
                perl -pi -e 's/-Wdeclaration-after-statement //g' ${i}
        done
}

PARALLEL_MAKE = ""

SRC_URI[md5sum] = "b8c118d4360846829beb30b02a6b91a7"
SRC_URI[sha256sum] = "e15d499321e003d12ed183601e37ee7ba5f64b278d1de30149ce01bd4a3f234d"
