DESCRIPTION = "Monotone is a distributed Source Control System"
HOMEPAGE = "http://www.venge.net/monotone"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "MIT"
DEPENDS = "boost"

S = "${WORKDIR}/monotone-${PV}"
# no cross compile support - it tries to run the test program even with
# --enable_ipv6=yes
EXTRA_OECONF = "--disable-ipv6 \
                --disable-dependency-tracking \
                --disable-rpath \
                --disable-nls \
                --with-gnu-ld \
                --with-bundled-lua --with-bundled-sqlite"

inherit autotools

# Release 0.22 reveals bugs in g++ for thumb (g++ generates
# relocations which the linker cannot represent)
#FIXME: remove the following
ARM_INSTRUCTION_SET = "arm"

PR = "r0"

SRC_URI = "http://venge.net/monotone/downloads/monotone-${PV}.tar.gz \
           file://txt2c-cross-post-0.22.patch \
	   file://uclibc.database.hh.stdarg.patch \
           file://configure.ac-no-sync-with-stdio-0.26.patch \
	   "
# This makes the testsuite as a package and renames the monotone executable
# to include the netsync suffix.
do_install_append() {
        install -d ${D}${tsd}
        install -c -m 755 testsuite ${D}${tsd}/testsuite
        cp -pPR tests ${D}${tsd}/tests
}

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-testsuite"
tsd = "/home/monotone/${PN}"
FILES_${PN}-testsuite = "${tsd}/testsuite ${tsd}/tests"
RDEPENDS_${PN}-testsuite += "bash sed grep cvs patch perl perl-modules"


SRC_URI[md5sum] = "cf3d8f26b7570d0d65834c3949913e2d"
SRC_URI[sha256sum] = "0a817e08b06141d78f2b8b0073aafd7110147679efb49c3e88c96a486532cacd"
