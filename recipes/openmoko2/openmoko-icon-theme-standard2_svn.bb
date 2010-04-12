DESCRIPTION = "Standard Gtk+ icon theme for the Openmoko framework"
HOMEPAGE = "http://www.openmoko.org"
SECTION = "openmoko/base"
LICENSE = "GPL"

SRCREV = "4232"
PV = "0.1.0+svnr${SRCPV}"
PR = "r3"

inherit autotools pkgconfig

SRC_URI = "svn://svn.openmoko.org/trunk/src/target/OM-2007.2/artwork/;module=icons;proto=http"
S = "${WORKDIR}/icons"

FILES_${PN} += "${datadir}/icons"

pkg_postinst_${PN} () {
    if [ "x$D" != "x" ]; then
        exit 1
    fi
    gtk-update-icon-cache -q /usr/share/icons/openmoko-standard
}

PACKAGE_ARCH = "all"
