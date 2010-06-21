DESCRIPTION = "G15daemon  takes control of the G15 keyboard, \
allowing the use of all keys through the linux kernel uinput \
device driver.  It  also controls  the use of the keyboard's \
LCD display, allows multiple, simultaneous client applications \
to connect, and gives  the  user the  ability to switch between \
client apps at the press of a button."
HOMEPAGE = "http://g15tools.sourceforge.net"
LICENSE = "GPLv2"
SECTION = "console/utils"
PRIORITY = "optional"
DEPENDS = "libdaemon libg15"
RDEPENDS_${PN} = "libg15"
RRECOMMENDS_${PN} = "kernel-module-uinput"
PR = "r3"

SRC_URI = "${SOURCEFORGE_MIRROR}/g15daemon/g15daemon-${PV}.tar.bz2"

inherit autotools

EXTRA_OECONF = "--with-gnu-ld"

do_stage () {
	autotools_stage_all
}


SRC_URI[md5sum] = "ba220f1fda33283af307c109cc520f61"
SRC_URI[sha256sum] = "583c4bece816b712959aba51f49d78bb587e215fbb6a322efe832477c74f2564"
