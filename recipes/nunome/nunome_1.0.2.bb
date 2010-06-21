DESCRIPTION = "Japanese input method plugin"
HOMEPAGE = "http://www.sikigami.com/nunome-Qtopia-1.0/"
SECTION = "opie/inputmethods"
PRIORITY = "optional"
LICENSE = "GPL"
RDEPENDS_${PN} = "virtual-japanese-font"
PR = "r5"

SRC_URI = "${SOURCEFORGE_MIRROR}/gakusei/nunome-${PV}.tar.bz2 \
	file://timer.patch \
	file://nunome.patch"
S = "${WORKDIR}/nunome"

inherit opie

EXTRA_QMAKEVARS_POST += "INCLUDEPATH+=${S}/Nnmlib INCLUDEPATH+=${S}/ui LIBS+=-L${S}"
OE_QMAKE_CXXFLAGS := "${@oe_filter_out('-fno-rtti', '${OE_QMAKE_CXXFLAGS}', d)}"
PARALLEL_MAKE = ""

do_configure_prepend() {
	sed -i -e 's,/opt/QtPalmtop/bin/,${bindir}/,g' "${S}/ui/nunome.h"
	sed -i -e 's,/opt/QtPalmtop/,${palmtopdir}/,g' "${S}/ui/nunome.h"
	printf "TEMPLATE=subdirs\nSUBDIRS=Nnmlib server ui dicman ui\n" >nunome.pro
	cd Nnmlib && qmake -project -t lib && cd ../
	cd server && qmake -project && printf "LIBS+=-lNnmlib\nTARGET=server.bin\n" >> server.pro && cd ../
	cd dicman && qmake -project && printf "LIBS+=-lnunome -lNnmlib\nTARGET=dicman.bin\n" >> dicman.pro && cd ../
	cd ui && qmake -project -t lib && printf "LIBS+=-lNnmlib\nTARGET=nunome\n" >> ui.pro && cd ../
	find . -name "moc*"|xargs rm -f
	find . -name "Makefile"|xargs rm -f
}

do_install() {
	install -d ${D}${palmtopdir}/lib
	install -d ${D}${bindir}
	install -d ${D}${palmtopdir}/i18n/ja
	install -d ${D}${palmtopdir}/share/nunome

	oe_libinstall -so libNnmlib ${D}${palmtopdir}/lib
        install -m 644 nunome_uni.dic  		${D}${palmtopdir}/share/nunome
        install -m 755 server.bin        	${D}${bindir}/nnmsrv
        install -m 755 dicman.bin		${D}${bindir}/nnmDicman
        install -m 644 ui/nunome.qm		${D}${palmtopdir}/i18n/ja
        install -m 644 dicman/nnmDicman.qm  	${D}${palmtopdir}/i18n/ja
}

SRC_URI[md5sum] = "e5c9d2351de250aaa7abf581a1f2c0f4"
SRC_URI[sha256sum] = "7bda9302036facd65b323d7042a809b08d55d5b61b38d4d40f22f41f27fec83c"
