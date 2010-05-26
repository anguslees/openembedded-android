SECTION = "console/network"
DESCRIPTION = "Iperf is a tool to measure maximum TCP bandwidth, allowing the tuning of various parameters and UDP characteristics"
HOMEPAGE = "http://dast.nlanr.net/Projects/Iperf/"
LICENSE = "BSD"
PR = "r1"

SRC_URI = "http://dast.nlanr.net/Projects/Iperf/iperf-${PV}-source.tar.gz \
	file://socketaddr-h-errno.diff"

inherit autotools

S="${WORKDIR}/iperf-${PV}/cfg"

do_configure() {
	oe_runconf
}

do_compile() {
	cd ${WORKDIR}/iperf-${PV}
	oe_runmake
}

do_install() {
	cd ${WORKDIR}/iperf-${PV}/src
	oe_runmake INSTALL_DIR=${D}${bindir} install
}

SRC_URI[md5sum] = "3e4aea85822bcf10ed14040f4b26bd26"
SRC_URI[sha256sum] = "f54fabaf344b1120e8682c7e5f1ef80d08d88f0b8640037f92e261e51c9c1672"
