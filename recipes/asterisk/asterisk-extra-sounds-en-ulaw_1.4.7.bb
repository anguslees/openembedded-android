DESCRIPTION="The Asterisk open source software PBX"
HOMEPAGE="http://www.asterisk.org"
SECTION = "voip"
LICENSE="GPL"
#DEPENDS="asterisk"
PR = "r1"

SRC_URI="http://downloads.digium.com/pub/telephony/sounds/releases/asterisk-extra-sounds-en-ulaw-${PV}.tar.gz"

do_install() {
	install -d ${D}${localstatedir}/lib/asterisk/sounds
	# HACK: moving the files in chunkes (too many of them)
	mv ${WORKDIR}/[a-d]*.ulaw ${D}${localstatedir}/lib/asterisk/sounds/
	mv ${WORKDIR}/[e-l]*.ulaw ${D}${localstatedir}/lib/asterisk/sounds/
	mv ${WORKDIR}/*.ulaw ${D}${localstatedir}/lib/asterisk/sounds/
	#install -m 644 ${WORKDIR}/*.ulaw ${D}${localstatedir}/lib/asterisk/sounds/

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/ha
	#install -m 644 ${WORKDIR}/ha/* ${D}${localstatedir}/lib/asterisk/sounds/ha/
	mv ${WORKDIR}/ha ${D}${localstatedir}/lib/asterisk/sounds

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/wx
	#install -m 644 ${WORKDIR}/wx/* ${D}${localstatedir}/lib/asterisk/sounds/wx/
	mv ${WORKDIR}/wx ${D}${localstatedir}/lib/asterisk/sounds
}

pkg_postinst_prepend() {
	chown -R asterisk:asterisk ${localstatedir}/lib/asterisk/sounds/
}



SRC_URI[md5sum] = "b8063676b102076321b6d4640b4e6126"
SRC_URI[sha256sum] = "94c2ba9290283851ddfba9e7b29c797412538d7a2002599508ce4122ad74c767"
