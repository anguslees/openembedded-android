DESCRIPTION = "A console wrapper for the FSO Usage API"
SECTION = "fso/base"
PRIORITY = "optional"
HOMEPAGE = "http://noko.sourceforge.net"
LICENSE = "GPL"
DEPENDS = "dbus"
SRCREV = "20"
PV = "0.0.1+svnr${SRCPV}"
PR = "r0"

SRC_URI = "svn://noko.svn.sourceforge.net/svnroot/noko/trunk;module=fsoraw;proto=https"
S = "${WORKDIR}/fsoraw/"

inherit autotools

# for forward compatibility in scripts
do_install_append() {
        install -d ${D}${bindir}
        ln -s fsoraw ${D}${bindir}/fso-raw
}
