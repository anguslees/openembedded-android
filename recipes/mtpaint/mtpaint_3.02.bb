DESCRIPTION = "mtPaint is a simple painting program"
SECTION = "x11/graphics"
DEPENDS = "gtk+ jpeg"
HOMEPAGE = "http://mtpaint.sf.net"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/mtpaint/mtpaint-3.02.tar.bz2"

do_configure() {
	/bin/bash ./configure gtk2
	cat > _conf.txt <<EOF
CC = ${CC}
MT_VERSION=mtPaint 3.02
MT_DATE=2006-10-14
LDFLAG = ${LDFLAGS} `pkg-config --libs gtk+-2.0` -ljpeg
CFLAG = ${CFLAGS} `pkg-config --cflags gtk+-2.0` -DVERSION="\"mtPaint 3.02"\" -DU_JPEG
subdirs = src
BIN_INSTALL="/usr/local/bin"
EOF
}

do_install() {
	install -d ${D}${bindir}
	install -m 0755 src/mtpaint ${D}${bindir}
}


SRC_URI[md5sum] = "828013176f135fc24ffdf16a011c51e6"
SRC_URI[sha256sum] = "991a1376dc23d0663d45b3b395c0e5c5542fee3906a120889583d67f854f5206"
