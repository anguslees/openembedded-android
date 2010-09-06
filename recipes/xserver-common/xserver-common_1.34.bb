DESCRIPTION = "Common X11 scripts and support files"
LICENSE = "GPL"
SECTION = "x11"
RDEPENDS_${PN} = "xmodmap xrandr xdpyinfo"
PR = "r0"

PACKAGE_ARCH = "all"
DEFAULT_PREFERENCE = "-1"

RCONFLICTS_${PN} = "xserver-kdrive-common"
RREPLACES_${PN} = "xserver-kdrive-common"

# we are using a gpe-style Makefile
inherit gpe

SRC_URI[md5sum] = "82f2f84cd96610e8f7b92c700cd31c14"
SRC_URI[sha256sum] = "cd04c33418f776b1e13fcc7af3d6bd0c7cccd03fbabd7dbcd97f88166cc34210"

SRC_URI_append = " \
                   file://setDPI.sh \
                   file://89xdgautostart.sh"

SRC_URI_append_angstrom = " file://xtscal-fix.patch "
RDEPENDS_${PN}_append_angstrom = " tslib-calibrate "
RDEPENDS_${PN}_append_shr = " xinput-calibrator "

SRC_URI_append_shr = " file://89xTs_Calibrate.xinput_calibrator.patch \
                       file://90xXWindowManager.patch \
                       file://Xserver.add.nocursor.for.gta.patch \
                       file://Xserver.add.xserver-system.patch \
                       file://Xserver.add.dpi.for.gta.patch"

do_install_append() {
	install -m 0755 "${WORKDIR}/setDPI.sh" "${D}/etc/X11/Xinit.d/50setdpi"
	install -m 0755 "${WORKDIR}/89xdgautostart.sh" "${D}/etc/X11/Xsession.d/89xdgautostart"
	sed -i 's:^BINDIR=.*$:BINDIR=${bindir}:' ${D}/etc/X11/xserver-common
}

