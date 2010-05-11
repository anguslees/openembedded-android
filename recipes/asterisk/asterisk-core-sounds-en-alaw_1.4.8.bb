DESCRIPTION="The Asterisk open source software PBX"
HOMEPAGE="http://www.asterisk.org"
SECTION = "voip"
LICENSE="GPL"
#DEPENDS="asterisk"
PR = "r1"

SRC_URI="http://downloads.digium.com/pub/telephony/sounds/releases/asterisk-core-sounds-en-alaw-${PV}.tar.gz"

do_install() {
	install -d ${D}${localstatedir}/lib/asterisk/sounds
	#install -m 644 ${WORKDIR}/*.alaw ${D}${localstatedir}/lib/asterisk/sounds/
	#install -m 644 ${WORKDIR}/CREDITS* ${D}${localstatedir}/lib/asterisk/sounds/
	#install -m 644 ${WORKDIR}/core-sounds-en.txt ${D}${localstatedir}/lib/asterisk/sounds/
	mv ${WORKDIR}/*.alaw ${D}${localstatedir}/lib/asterisk/sounds/

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/dictate
	#install -m 644 ${WORKDIR}/dictate/* ${D}${localstatedir}/lib/asterisk/sounds/dictate/
	mv ${WORKDIR}/dictate ${D}${localstatedir}/lib/asterisk/sounds/

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/digits
	#install -m 644 ${WORKDIR}/digits/* ${D}${localstatedir}/lib/asterisk/sounds/digits/
	mv ${WORKDIR}/digits ${D}${localstatedir}/lib/asterisk/sounds/

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/followme
	#install -m 644 ${WORKDIR}/followme/* ${D}${localstatedir}/lib/asterisk/sounds/followme/
	mv ${WORKDIR}/followme ${D}${localstatedir}/lib/asterisk/sounds/

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/letters
	#install -m 644 ${WORKDIR}/letters/* ${D}${localstatedir}/lib/asterisk/sounds/letters/
	mv ${WORKDIR}/letters ${D}${localstatedir}/lib/asterisk/sounds/

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/phonetic
	#install -m 644 ${WORKDIR}/phonetic/* ${D}${localstatedir}/lib/asterisk/sounds/phonetic/
	mv ${WORKDIR}/phonetic ${D}${localstatedir}/lib/asterisk/sounds/

	#install -d ${D}${localstatedir}/lib/asterisk/sounds/silence
	#install -m 644 ${WORKDIR}/silence/* ${D}${localstatedir}/lib/asterisk/sounds/silence/
	mv ${WORKDIR}/silence ${D}${localstatedir}/lib/asterisk/sounds/
}

pkg_postinst_prepend() {
	chown -R asterisk:asterisk ${localstatedir}/lib/asterisk/sounds/
}



SRC_URI[md5sum] = "e708a5510a61217725b6ad663287dfa0"
SRC_URI[sha256sum] = "5d5c305079eb9918d271fad28ac73248a7d2962347dbd80d16aac66fe35e2729"
