DESCRIPTION = "High level Session Initiation Protocol (SIP) library"
SECTION = "libs"
PRIORITY = "optional"
LICENSE = "GPL"
DEPENDS = "libosip2"
SRCNAME = "libeXosip2"
LEAD_SONAME = "libeXosip2"

PR = "r0"
SRC_URI = "http://download.savannah.nongnu.org/releases/exosip/${SRCNAME}-${PV}.tar.gz \
           file://simplify-flags.patch"
S = "${WORKDIR}/${SRCNAME}-${PV}"

inherit autotools pkgconfig
EXTRA_OECONF = "--disable-josua"

do_stage() {
        autotools_stage_all
}

SRC_URI[md5sum] = "837a35f085890eac3328ac402b24383b"
SRC_URI[sha256sum] = "a09e97847f3983431c5453bf2f3de1629e629c1b4ea9ddc5e3aa6caaf7417b05"
