DESCRIPTION = "Monotone is a distributed Source Control System"
HOMEPAGE = "http://www.monotone.ca/"
SECTION = "devel"
PRIORITY = "optional"
LICENSE = "MIT"
DEPENDS = "boost"

PROVIDES = "monotone"
RPROVIDES = "monotone"

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

SRC_URI = "http://monotone.ca/downloads/${PV}/monotone-${PV}.tar.gz \
           file://txt2c-cross-post-0.22.patch \
	   file://uclibc.database.hh.stdarg.patch \
	   file://configure.ac-no-sync-with-stdio-0.31.patch \
	  "
do_compile_append() {
	touch testsuite
}

# This makes the testsuite as a package and renames the monotone executable
# to include the netsync suffix.
do_install_append() {
        install -d ${D}${tsd}
        install -c -m 755 testsuite ${D}${tsd}/testsuite
        cp -pPR tests ${D}${tsd}/tests
	rm ${D}/home/monotone/monotone-6/tests/diff_a_binary_file/binary
}

PACKAGES = "${PN}-dbg ${PN} ${PN}-doc ${PN}-testsuite"
tsd = "/home/monotone/${PN}"
FILES_${PN}-testsuite = "${tsd}/testsuite ${tsd}/tests"
RDEPENDS_${PN}-testsuite += "bash sed grep cvs patch perl perl-modules"


SRC_URI[md5sum] = "b7a3cd2d4fcb17287641ee2e33f660fd"
SRC_URI[sha256sum] = "57cc436480dd29ac7e751c4d5adbc2b0289ba2273bda388352be15446894bc56"
