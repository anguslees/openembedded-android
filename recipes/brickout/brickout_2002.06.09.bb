DESCRIPTION = "Clone of the classic arcade game Asteroids for Qt/Embedded based palmtop environments w/ SDL"
SECTION = "opie/games"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "libsdl-qpe libsdl-mixer libsdl-image"
PR = "r4"

SRC_URI = "ftp://ftp.billsgames.com/unix/agenda/brickout/src/brickout-${PV}.tar.gz \
           file://directories.patch"

inherit palmtop

EXTRA_QMAKEVARS_POST += " INCLUDEPATH+=${STAGING_INCDIR}/SDL LIBS+=-lSDL LIBS+=-lSDL_mixer LIBS+=-lSDLmain \
			LIBS+=-lSDL_image LIBS+=-lpthread TARGET=brickout DEFINES+=USE_SDL "

do_configure_prepend() {
        qmake -project -o brickout.pro
}

do_install() {
        install -d ${D}${palmtopdir}/bin \
		   ${D}${palmtopdir}/apps/Games \
		   ${D}${palmtopdir}/pics \
		   ${D}${palmtopdir}/share/brickout/sounds \
		   ${D}${palmtopdir}/share/brickout/music \
		   ${D}${palmtopdir}/share/brickout/images
        install -D -m 0755 brickout ${D}${palmtopdir}/bin/brickout
	install -D -m 0644 brickout.png ${D}${palmtopdir}/pics/brickout.png

        cp -pPR sounds/* ${D}${palmtopdir}/share/brickout/sounds/
        cp -pPR music/* ${D}${palmtopdir}/share/brickout/music/
        cp -pPR images-sdl/* ${D}${palmtopdir}/share/brickout/images/

	echo "[Desktop Entry]" >${D}${palmtopdir}/apps/Games/brickout.desktop
	echo "Comment=Arkanoid game" >>${D}${palmtopdir}/apps/Games/brickout.desktop
	echo "Exec=brickout" >>${D}${palmtopdir}/apps/Games/brickout.desktop
	echo "Icon=brickout" >>${D}${palmtopdir}/apps/Games/brickout.desktop
	echo "Type=Application" >>${D}${palmtopdir}/apps/Games/brickout.desktop
	echo "Name=Brickout" >>${D}${palmtopdir}/apps/Games/brickout.desktop
}

SRC_URI[md5sum] = "f191a5658e4262e85499e5b69433af89"
SRC_URI[sha256sum] = "56e0abb8697b62949ab085419a3b6f83dd13db02d322759f4cd6c5e5286c2240"
