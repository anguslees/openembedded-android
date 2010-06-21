DESCRIPTION = "Remoko -- Bluetooth Remote Control"
HOMEPAGE = "http://code.google.com/p/remoko/"
AUTHOR = "Valério Valério"
LICENSE = "GPL"
SECTION = "console/network"
DEPENDS = "edje-native"
SRCREV = "121"
PV = "0.3.2+svnr${SRCPV}"
PR = "r1"
PE = "1"

SRC_URI = "svn://remoko.googlecode.com/svn/trunk;module=BT_HID_UI;proto=http"
S = "${WORKDIR}/BT_HID_UI"

inherit distutils

RDEPENDS_${PN} = "remoko-server python-evas python-ecore python-edje python-edbus"
FILES_${PN} += "${datadir}"
