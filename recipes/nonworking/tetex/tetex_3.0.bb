DESCRIPTION = "teTeX is a complete (pdf)(La)TeX distribution for UNIX compatible systems"
HOMEPAGE = "http://www.tug.org/tetex"
LICENSE = "GPL"
SECTION = "console/utils"
DEPENDS = "tetex-native flex gd ncurses libpng t1lib virtual/libx11 libxau libxext libxt zlib"
PR = "r6"

SRC_URI = "ftp://dante.ctan.org/tex-archive/systems/unix/teTeX/current/distrib/tetex-src-${PV}.tar.gz \
           file://configure.patch"
S = "${WORKDIR}/tetex-src-${PV}"

inherit autotools

PARALLEL_MAKE = ""

export BUILDCC = "${BUILD_CC}"
export BUILDCFLAGS = "${BUILD_CFLAGS}"
export BUILDLDFLAGS = "${BUILD_LDFLAGS}"
export BUILDCCLD = "${BUILD_CC}"

EXTRA_OECONF = "--with-system-libgd \
                --with-system-ncurses \
                --with-ncurses-include=${STAGING_INCDIR} \
                --with-system-pnglib \
                --with-system-t1lib \
                --with-system-zlib \
                --without-dialog \
                --without-xdvik \
                --with-x11 \
                --without-mf-x-toolkit"

# NOTE:  In theory, teTeX has a good buildsystem, which automatically detects
#        whether we are cross-compiling and compiles the necessary host tools.
#        Unfortunately it doesn't work in our case and we better just add
#        tetex-native for the time being.
#        It would be good to autoreconf all the stuff, but the upstream configure.in
#        is faulty and outdated :/
do_configure() {
        rm -f texk/libtool.m4
        libtoolize --force
        gnu-configize
        oe_runconf ${EXTRA_OECONF}
        find . -name libtool|xargs rm -f
        rm -rf ${S}/utils/texinfo/tools/info
        rm -rf ${S}/utils/texinfo/tools/makeinfo
        ln -sf ${STAGING_BINDIR_NATIVE} ${S}/utils/texinfo/tools/info
        ln -sf ${STAGING_BINDIR_NATIVE} ${S}/utils/texinfo/tools/makeinfo
        cat >${S}/utils/texinfo/tools/Makefile <<EOF
install:
	echo "mickey _is_ cool - he tamed the tetex buildsystem"
all:
	echo "mickey sucks - he adds easter eggs in output that no one will ever read..."
EOF
}
# NOTE: Make sure it is using _our_ libtool and nothing else :/
do_compile() {
	LIBTOOL="${STAGING_BINDIR_NATIVE}/${HOST_SYS}-libtool" oe_runmake MAKE="${MAKE} LIBTOOL=${STAGING_BINDIR_NATIVE}/${HOST_SYS}-libtool"
}

# NOTE: This is really ugly. Unfortunately the teTeX people seem not to know about PREFIX...
do_install() {
	install -d ${D}${bindir}
	install -d ${D}${libdir}
	install -d ${D}${datadir}/texmf
	install -d ${D}${localstatedir}/lib/textmf

	export bindir="${D}${bindir}" \
	sbindir="${D}${sbindir}" \
	libexecdir="${D}${libexecdir}" \
	datadir="${D}${datadir}" \
	sysconfdir="${D}${sysconfdir}" \
	sharedstatedir="${D}${sharedstatedir}" \
	localstatedir="${D}${localstatedir}" \
	libdir="${D}${libdir}" \
	includedir="${D}${includedir}" \
	oldincludedir="${D}${oldincludedir}" \
	infodir="${D}${infodir}" \
	mandir="${D}${mandir}" \
	texmf="${D}${datadir}/texmf" \
	scriptdir="${D}${bindir}" \
	web2cdir="${D}${datadir}" \
	kpathsea="${D}${libdir}/libkpathsea.la" \
	DESTDIR="" \
	LIBTOOL="${STAGING_BINDIR_NATIVE}/${HOST_SYS}-libtool"
	MAKE="make -e" oe_runmake -e install
        ln -sf ${bindir}/pdfetex ${D}${bindir}/latex
	mv ${D}${libdir}/.libs/* ${D}${libdir}/ -f || true
}

RRECOMMENDS_${PN} = "tetex-bin tetex-texmf-dvips tetex-texmf-texconfig tetex-texmf-fonts"
PACKAGES =+ "libkpathsea tetex-bin tetex-texmf-dvips tetex-texmf-texconfig tetex-texi2html"

FILES_libkpathsea = "${libdir}/libkpathsea.so.*"
FILES_tetex-bin = "${bindir}/*"

FILES_${PN} += "${localstatedir} ${datadir}"
FILES_${PN}-doc += "${datadir}/texinfo ${datadir}/man ${datadir}/info"
FILES_tetex-texmf-dvips = "${datadir}/texmf/dvips"
FILES_tetex-texmf-texconfig = "${datadir}/texmf/texconfig"
FILES_tetex-texi2html = "${datadir}/texi2html"

SRC_URI[md5sum] = "944a4641e79e61043fdaf8f38ecbb4b3"
SRC_URI[sha256sum] = "9c0f7eaeb5ba6dc6f66433404d264941bf95cded2fa798b1f7a9dd580c21649b"
