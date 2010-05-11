DESCRIPTION = "NSS module for Multicast DNS name resolution"
HOMEPAGE = "http://0pointer.de/lennart/projects/nss-mdns/"
LICENSE = "GPL"
SECTION = "libs"
PRIORITY = "optional"

RRECOMMENDS_${PN} = "zeroconf"
PR = "r0"

EXTRA_OECONF = "--libdir=/lib"
S = "${WORKDIR}/nss-mdns-${PV}"

SRC_URI = "http://0pointer.de/lennart/projects/nss-mdns/nss-mdns-${PV}.tar.gz"

inherit autotools

pkg_postinst () {
        # can't do this offline
        if [ "x$D" != "x" ]; then
                exit 1
        fi
	cat /etc/nsswitch.conf | grep "hosts:\s*files dns$" > /dev/null && {
		cat /etc/nsswitch.conf | sed 's/\(hosts:\s*files \)dns/\1mdns4_minimal [NOTFOUND=return] dns mdns4/' > /tmp/nsswitch.conf
		mv /tmp/nsswitch.conf /etc/nsswitch.conf
	}
}

pkg_prerm () {
	cat /etc/nsswitch.conf | grep "hosts:\s*files dns mdns$" > /dev/null && {
		cat /etc/nsswitch.conf | sed 's/\(hosts:\s*files \)mdns4_minimal [NOTFOUND=return] dns mdns4/\1dns/' > /tmp/nsswitch.conf
		mv /tmp/nsswitch.conf /etc/nsswitch.conf
	}
}

SRC_URI[md5sum] = "bc72f5b19cc6ce8cacde448236b30868"
SRC_URI[sha256sum] = "0bf226bb3a1716e6eb97355e08a7ffcf09aadfb91ba41ccef2ef1ba7a01719a2"
