DESCRIPTION = "Simple Xserver Init Script (no dm)"
LICENSE = "GPL"
SECTION = "x11"
PRIORITY = "optional"
RDEPENDS_${PN} = "xserver-common (>= 1.30) xinit"
DEFAULT_PREFERENCE = "-1"
PR = "r1"

SRC_URI = "file://xserver-nodm"
S = ${WORKDIR}

PACKAGE_ARCH = "all"

do_install() {
    install -d ${D}/etc
    install -d ${D}/etc/init.d
    install xserver-nodm ${D}/etc/init.d
}    

inherit update-rc.d

INITSCRIPT_NAME = "xserver-nodm"
INITSCRIPT_PARAMS = "start 01 5 2 . stop 01 1 6 ."
