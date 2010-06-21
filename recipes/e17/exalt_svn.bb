DESCRIPTION = "Exalt is a network manager for the windows manager Enlightenment DR17."
HOMEPAGE = "http://watchwolf.fr/wiki/doku.php?id=exalt"

LICENSE = "LGPLv2"
DEPENDS = "elementary vpnc wpa-supplicant ecore eet edbus"
RDEPENDS_${PN} = "vpnc wpa-supplicant"

PV = "0.9+svnr${SRCPV}"
SRCREV = "${EFL_SRCREV}"
PR = "r1"

inherit e

EXTRA_OECONF = " \
--with-wpa_supplicant=${sbindir}/wpa_supplicant \
--with-vpnc=${sbindir}/vpnc \
--with-vpnc-disconnect=${sbindir}/vpnc-disconnect \
"

do_install_append() {
	# install dbus config file
	install -d ${D}${sysconfdir}/dbus-1/system.d/
	install -m 0644 data/daemon/dbus/exalt.conf ${D}${sysconfdir}/dbus-1/system.d/exalt.conf

	# install dbus service file
	install -d ${D}${datadir}/dbus-1/system-services/	
	install -m 0644 org.e.Exalt.service ${D}${datadir}/dbus-1/system-services/
}

FILES_${PN} += "${datadir}/dbus-1"

