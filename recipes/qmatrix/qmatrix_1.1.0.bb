DESCRIPTION = "A matrix Game, Qt/Embedded based Palmtop Environments Edition"
SECTION = "opie/games"
LICENSE = "GPL"
PRIORITY = "optional"
PR = "r2"

SRC_URI = "http://www.geocities.co.jp/SiliconValley-Oakland/8074/src/qmatrix-${PV}.tar.gz"
S = "${WORKDIR}/qmatrix"

inherit palmtop

MACHTYPE = "sl300"
MACHTYPE_c7x0    = "sl700"
MACHTYPE_tosa    = "sl700"

do_configure_append() {
        ln -v -f -s config/config-${MACHTYPE}.h config.h
        ln -v -f -s config/config.mk-${MACHTYPE} config.mk
        if [ "${MACHTYPE}" = "sl300" ]
        then
                ln -vfs bigFishSize.h areaSize.h
        else
                ln -vfs smallFishSize.h areaSize.h
        fi
        ln -v -f -s HiScoreRegConfig-${MACHTYPE}.h HiScoreRegConfig.h
}

do_compile_prepend() {
	cd images && oe_runmake && cd ${S}
}

do_install() {
	install -d ${D}${palmtopdir}/apps/Games
	install -m 0644 desktop/qmatrix-${MACHTYPE}.desktop ${D}${palmtopdir}/apps/Games/qmatrix.desktop
	install -d ${D}${palmtopdir}/bin
	install -m 0755 qmatrix ${D}${palmtopdir}/bin/qmatrix
	install -d ${D}${palmtopdir}/pics
	install -m 0644 desktop/qmatrix.png ${D}${palmtopdir}/pics/
	install -d ${D}${palmtopdir}/help/html/
	install -m 0644 desktop/qmatrix.html ${D}${palmtopdir}/help/html/qmatrix.html
}

SRC_URI[md5sum] = "03c874e403fcf1566d96a9a064cf73c9"
SRC_URI[sha256sum] = "57d9c0be4714f9973ce50f70d77c68b874cc5456cab2d210861a4b1ee32cb67f"
