DESCRIPTION = "A C64 emulator based on SDL."
SECTION = "opie/applications"
DEPENDS = "libsdl-qpe"
LICENSE = "GPL"
PR = "r1"

SRC_URI = "http://frodo.cebix.net/downloads/FrodoV4_1b.Src.tar.gz \
           file://frodo-qte.diff \
           file://frodo-joystick-4state.diff \
           file://frodorc \
           file://Frodo.png \
           file://frodo.desktop"
S = "${WORKDIR}/Frodo-${PV}/Src"

inherit autotools

EXTRA_OECONF = "--disable-sdltest --enable-qtopia"

do_configure() {
	gnu-configize
	oe_runconf
}

do_install() {
        install -d ${D}${palmtopdir}/bin \
        	   ${D}${palmtopdir}/apps/Games \
        	   ${D}${palmtopdir}/pics
	install -m 0644 ${WORKDIR}/Frodo.png ${D}${palmtopdir}/pics/Frodo.png
	install -m 0644 ${WORKDIR}/frodo.desktop ${D}${palmtopdir}/apps/Games/frodo.desktop
	# start script and executable
	install -d ${D}${palmtopdir}/bin/${PN}
	install -m 0755 Frodo ${D}${palmtopdir}/bin/${PN}/Frodo
	cat <<STARTER_EOF > ${D}${palmtopdir}/bin/${PN}/frodostart
#!/bin/sh
cd ${palmtopdir}/bin/${PN}
./Frodo
STARTER_EOF
	chmod 0755 ${D}${palmtopdir}/bin/${PN}/frodostart
	ln -sf ${PN}/frodostart ${D}${palmtopdir}/bin/Frodo
	# conffiles
	install -d ${D}${sysconfdir}
	install -m 644 ${WORKDIR}/frodorc ${D}${sysconfdir}/frodorc
}

FILES_${PN} = "${palmtopdir} ${sysconfdir}"

#FIXME: Add postinst which copies /etc/frodorc into $HOME/.frodorc

SRC_URI[md5sum] = "095b9f21c03204cc13f7f249e8866cd9"
SRC_URI[sha256sum] = "ffae146dc20458698acf5ccc384dc32317cea1df0c95b44b5d47cca8b26bc986"
