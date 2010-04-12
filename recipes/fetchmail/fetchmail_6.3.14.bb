DESCRIPTION = "Fetchmail is a free, full-featured, robust, \
well-documented remote-mail retrieval and forwarding utility \
intended to be used over on-demand TCP/IP links \
(such as SLIP or PPP connections)."
SECTION = "console/network"
LICENSE = "GPL"
DEPENDS = "python-native"

PR = "r3"

SRC_URI = "http://download.berlios.de/fetchmail/${PN}-${PV}.tar.bz2;name=src \
	   "

SRC_URI[src.md5sum] = "86d3cfbce151881d8bf76a1efd5bda6a"
SRC_URI[src.sha256sum] = "8657f786f5af1857ddb3b50203e6cde2efad43f49828973cbb22f6d431208607"

inherit autotools gettext python-dir

FILES_${PN} = "${bindir}/fetchmail"
PACKAGES += "${PN}conf"
FILES_${PN}conf = "${PYTHON_SITEPACKAGES_DIR}/fetchmailconf.py* ${bindir}/fetchmailconf"
RDEPENDS_${PN}conf = "${PN} python-core"
