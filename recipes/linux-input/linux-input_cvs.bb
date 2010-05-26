PR = "r2"
SRCDATE = "20060814"
PV = "0.0+cvs${SRCDATE}"

SRC_URI = "cvs://anonymous@linuxconsole.cvs.sourceforge.net/cvsroot/linuxconsole;module=ruby \
	file://serio.h \
	file://makefile.patch;striplevel=0 \
	file://snes232.patch;striplevel=0"
LICENSE = "GPL"

S = "${WORKDIR}/ruby/utils"

CFLAGS =+ "-I. -I../linux/include -I../ruby-2.6/include"

do_configure() {
	install -d linux
	install -m 0644 ${WORKDIR}/serio.h linux/
}

do_install() {
	install -d ${D}${sbindir}
	install evtest inputattach jstest jscal fftest ${D}${sbindir}
}
