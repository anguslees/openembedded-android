DESCRIPTION = "AbiWord is a free word processing program similar to Microsoft(r) Word"
HOMEPAGE = "http://www.abiword.org"
SECTION = "x11/office"
LICENSE = "GPLv2"
DEPENDS = "libwpd librsvg goffice poppler"
RDEPENDS = "abiword"

PR = "r1"

SRC_URI = "http://www.abiword.org/downloads/abiword/${PV}/source/abiword-${PV}.tar.gz \
           file://abiword-plugin-pdf-poppler.patch;striplevel=2"
S = "${WORKDIR}/abiword-${PV}/abiword-plugins"

inherit autotools

PARALLEL_MAKE=""

EXTRA_OECONF = "--without-libwmf --without-inter7eps"

PACKAGES_DYNAMIC = "abiword-plugin-*"

python populate_packages_prepend () {
	abiword_libdir = bb.data.expand('${libdir}/AbiWord-2.4/plugins', d)

	do_split_packages(d, abiword_libdir, '^libAbi(.*)\.so$', 'abiword-plugin-%s', 'Abiword plugin for %s', extra_depends='')
}


SRC_URI[md5sum] = "30fbd0a9b539f15f54f90d3812a75266"
SRC_URI[sha256sum] = "98105f03b1f33f26cddbfc18d64b04e64a85db3c861508837ab290d26ca2fed3"
