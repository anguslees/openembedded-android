SECTION = "console/network"
DESCRIPTION = "This program is used to forward DHCP and BOOTP messages between two \
 networks with different broadcast domains. \
 It works better with ppp - and especially with ipsec over ppp - than \
 dhcp-relay from ISC and has a smaller foot print."
HOMEPAGE = "http://www.nongnu.org/dhcp-fwd/"
LICENSE = "GPLv2"

SRC_URI = "http://savannah.nongnu.org/download/dhcp-fwd/dhcp-forwarder-${PV}.tar.bz2 \
	file://init \
	file://dhcp-fwd.cfg"

inherit autotools update-rc.d

EXTRA_OECONF="--disable-dietlibc"

INITSCRIPT_NAME="dhcp-forwarder"
INITSCRIPT_PARAMS="defaults"

do_install_append() {
	install -d ${D}${sysconfdir}/init.d
	install -m 0755 ${WORKDIR}/init ${D}${sysconfdir}/init.d/dhcp-forwarder
	install -m 0644 ${WORKDIR}/dhcp-fwd.cfg ${D}${sysconfdir}
}

CONFFILES_${PN}_nylon = "${sysconfdir}/dhcp-fwd.cfg"

SRC_URI[md5sum] = "cbe60c8c904394a8e38e12ac42c02284"
SRC_URI[sha256sum] = "45b708fb49a9fab10d814eb1e340a0960dac91f92800b3d39ddf0245ac913e30"
