DESCRIPTION = "A Client for Wi-Fi Protected Access (WPA)."
SECTION = "network"
LICENSE = "GPL"
HOMEPAGE = "http://hostap.epitest.fi/wpa_supplicant/"
DEPENDS = "openssl"
DEPENDS_append_mtx-1 = " madwifi-modules"
DEPENDS_append_mtx-2 = " madwifi-modules"
PR = "r1"

SRC_URI = "http://hostap.epitest.fi/releases/wpa_supplicant-${PV}.tar.gz \
	file://madwifi-bsd-fix.diff;striplevel=0 \
	file://defconfig \
        file://driver-hermes.patch \
	file://driver-zd1211.patch \
	file://wpa_supplicant.conf"

S = "${WORKDIR}/wpa_supplicant-${PV}"

do_configure () {
	install -m 0755 ${WORKDIR}/defconfig  .config
}

do_compile () {
	make
}

do_install () {
	install -d ${D}${sbindir}
	install -m 755 wpa_supplicant ${D}${sbindir}
	install -m 755 wpa_passphrase ${D}${sbindir}
	install -m 755 wpa_cli        ${D}${sbindir}

	install -d ${D}${sysconfdir}
	install -m 644 ${WORKDIR}/wpa_supplicant.conf ${D}${sysconfdir}

	install -d ${D}${docdir}/wpa_supplicant
	install -m 644 README ${D}${docdir}/wpa_supplicant
}

SRC_URI[md5sum] = "1345730f15d5f93f2f083096ddc903eb"
SRC_URI[sha256sum] = "002a5d52c1c8f516efe6a834227d3fe63ecf2588966e44fbde1e72a5844c334f"
