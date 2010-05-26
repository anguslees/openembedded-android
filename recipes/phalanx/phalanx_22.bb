DESCRIPTION = "Phalanx is a chess playing engine."
SECTION = "opie/libs"
PRIORITY = "optional"
PR = "r4"
LICENSE = "GPL"
SRC_URI = "http://ftp.debian.org/debian/pool/main/p/phalanx/phalanx_22.orig.tar.gz \
           file://gcc3.patch \
           file://capabilities \
           file://description"
S = "${WORKDIR}/phalanx-22.orig"

do_compile() {
	oe_runmake CC="${CC}" CFLAGS="${CFLAGS}" STRIP=echo LD="${CC}"
}

do_install() {
	install -d ${D}${palmtopdir}/chess/engines/Phalanx
        install -D -m 755 phalanx ${D}${palmtopdir}/chess/engines/Phalanx/phalanx
        install -D -m 755 pbook.phalanx ${D}${palmtopdir}/chess/engines/Phalanx/pbook.phalanx
        >${D}${palmtopdir}/chess/engines/Phalanx/sbook.phalanx
        >${D}${palmtopdir}/chess/engines/Phalanx/learn.phalanx
        install -D -m 755 ${WORKDIR}/capabilities ${D}${palmtopdir}/chess/engines/Phalanx/capabilities
        install -D -m 755 ${WORKDIR}/description ${D}${palmtopdir}/chess/engines/Phalanx/description
}

FILES_${PN} = "${palmtopdir}/chess"
FILES_${PN}-dbg += "${palmtopdir}/chess/engines/Phalanx/.debug"

SRC_URI[md5sum] = "e4e6155530a23ea0ea4ca59f8c5fda8c"
SRC_URI[sha256sum] = "e86a0a81ce1b989a4d06e0c07d64e1fcbba2456fa8e22ae24f333f186f3fc663"
