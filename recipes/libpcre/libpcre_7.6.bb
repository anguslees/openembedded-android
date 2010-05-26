DESCRIPTION = "Perl-compatible regular expression library. PCRE has its own native \
API, but a set of 'wrapper' functions that are based on the POSIX API \
are also supplied in the library libpcreposix. Note that this just \
provides a POSIX calling interface to PCRE; the regular expressions \
themselves still follow Perl syntax and semantics. The header file for \
the POSIX-style functions is called pcreposix.h."
SECTION = "devel"
PR = "r7"
LICENSE = "BSD"
SRC_URI = "${SOURCEFORGE_MIRROR}/pcre/pcre-${PV}.tar.bz2 \
           file://pcre-cross.patch"
S = "${WORKDIR}/pcre-${PV}"

PROVIDES = "pcre"

inherit autotools binconfig

PARALLEL_MAKE = ""

CFLAGS_append = " -D_REENTRANT"
CXXFLAGS_powerpc += "-lstdc++"
EXTRA_OECONF = " --with-link-size=2 --enable-newline-is-lf --with-match-limit=10000000 --enable-rebuild-chartables --enable-utf8"

do_compile () {
	# stop libtool from trying to link with host libraries - fix from #33
	# this resolve build problem on amd64 - #1015
	if [ -e ${S}/${TARGET_SYS}-libtool ] ; then
		sed -i 's:-L\$:-L${STAGING_LIBDIR} -L\$:' ${S}/${TARGET_SYS}-libtool
	else
		ln -sf ${S}/libtool ${S}/${TARGET_SYS}-libtool
		sed -i 's:-L\$:-L${STAGING_LIBDIR} -L\$:' ${S}/${TARGET_SYS}-libtool	
	fi

	# The generation of dftables can lead to timestamp problems with ccache
	# because the generated config.h seems newer.  It is sufficient to ensure that the
	# attempt to build dftables inside make will actually work (foo_FOR_BUILD is
	# only used for this).
	oe_runmake CC_FOR_BUILD="${BUILD_CC}" CFLAGS_FOR_BUILD="-DLINK_SIZE=2 -I${S}/include" LINK_FOR_BUILD="${BUILD_CC} -L${S}/lib"
}

do_install_append () {
    install -d ${STAGING_BINDIR}
    install -m 0755 ${D}${bindir}/pcre-config ${STAGING_BINDIR}/
}

python populate_packages_prepend () {
	pcre_libdir = bb.data.expand('${libdir}', d)
	pcre_libdir_dbg = bb.data.expand('${libdir}/.debug', d)
	do_split_packages(d, pcre_libdir, '^lib(.*)\.so$', 'lib%s-dev', 'libpcre %s development package', extra_depends='${PN}-dev', allow_links=True)
	do_split_packages(d, pcre_libdir, '^lib(.*)\.la$', 'lib%s-dev', 'libpcre %s development package', extra_depends='${PN}-dev')
	do_split_packages(d, pcre_libdir, '^lib(.*)\.a$', 'lib%s-dev', 'libpcre %s development package', extra_depends='${PN}-dev')
	do_split_packages(d, pcre_libdir, '^lib(.*)\.so\.*', 'lib%s', 'libpcre %s library', extra_depends='', allow_links=True)
}

FILES_${PN} = "${libdir}/libpcre.so.*"
FILES_${PN}-dev += "${bindir}/*"

SRC_URI[md5sum] = "2af38e083fb90ef60fa9eda7cc290e86"
SRC_URI[sha256sum] = "362e4b4473f2f7a3bfa28ea73e80ec00a2fe525a1aceb5f66e1c528a900bd735"
