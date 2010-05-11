DESCRIPTION = "Skin for opie-mediaplayer2"
SECTION = "opie/multimedia"
PRIORITY = "optional"
LICENSE = "GPL"
APPNAME = "opieplayer2"
RPROVIDES = "opie-mediaplayer2-skin"
OPIE_CVS_PV ?= "1.2.2+cvs${SRCDATE}"
PV = "${OPIE_CVS_PV}"
PR = "r1"

SRC_URI = "${HANDHELDS_CVS};module=opie/pics"

do_install() {
	install -d ${D}${palmtopdir}/pics/${APPNAME}/skins/default/
        install -m 0644 ${WORKDIR}/pics/${APPNAME}/skins/default/*.png ${D}${palmtopdir}/pics/${APPNAME}/skins/default/
}

FILES_${PN} = "${palmtopdir}/pics/${APPNAME}/skins/default/*.png"
