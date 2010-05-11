DESCRIPTION="The Asterisk open source software PBX"
HOMEPAGE="http://www.asterisk.org"
SECTION = "voip"
LICENSE="GPL"
#DEPENDS="asterisk"
PR = "r1"

SRC_URI="http://downloads.digium.com/pub/telephony/sounds/releases/asterisk-moh-freeplay-alaw.tar.gz"

do_install() {
	install -d ${D}${localstatedir}/lib/asterisk/moh
	#install -m 644 ${WORKDIR}/*.alaw ${D}${localstatedir}/lib/asterisk/moh/
	#install -m 644 ${WORKDIR}/LICENSE-* ${D}${localstatedir}/lib/asterisk/moh/
	mv ${WORKDIR}/*.alaw ${D}${localstatedir}/lib/asterisk/moh/
	mv ${WORKDIR}/LICENSE-* ${D}${localstatedir}/lib/asterisk/moh/
}

pkg_postinst_prepend() {
	chown -R asterisk:asterisk ${localstatedir}/lib/asterisk/moh/
}



SRC_URI[md5sum] = "ef6dca03deeaf1cfd2c97feb5af21538"
SRC_URI[sha256sum] = "6dec5684b48cb375c226ed94aeee77e35b9db6c7e85abeb9bf5260e8df43cd9c"
# CHECKSUMS.INI MISMATCH: I've got this instead:
#SRC_URI[md5sum] = "386b8acb431760ea40d405fec69f67bb"
#SRC_URI[sha256sum] = "8b6d63486fd58fd535eaed394f9bd32ecdf6e650975aaa258941f423c8150b81"

