DESCRIPTION = "Common X11 scripts and support files"
LICENSE = "GPL"
SECTION = "x11"
RDEPENDS_${PN} = "xmodmap xrandr xdpyinfo"
PR = "r5"

PACKAGE_ARCH = "all"

# we are using a gpe-style Makefile
inherit gpe

SRC_URI_append = " file://setDPI.sh \
                   file://89xdgautostart.sh \
                   file://avoid-rotated-server.patch \
                   file://ts-handling-cleanup.diff \
                   file://Xserver-at91.patch \
"

SRC_URI_append_angstrom = " file://xtscal-fix.patch "
RDEPENDS_${PN}_append_angstrom = " tslib-calibrate "

do_install_append() {
	install -m 0755 "${WORKDIR}/setDPI.sh" "${D}/etc/X11/Xinit.d/50setdpi"
	install -m 0755 "${WORKDIR}/89xdgautostart.sh" "${D}/etc/X11/Xsession.d/89xdgautostart"
	sed -i 's:^BINDIR=.*$:BINDIR=${bindir}:' ${D}/etc/X11/Xserver
}

SRC_URI[md5sum] = "95faf3cc8c0a70352e9580fc873ca693"
SRC_URI[sha256sum] = "f0391d08a6763598066e21d83460493973fe24fc65e3f0d2b534ef2497946a34"
