require xserver-kdrive-common.inc

DEPENDS += "hal libxkbfile libxcalibrate pixman"
RDEPENDS += "hal"

PE = "1"
PR = "${INC_PR}.1"

SRC_URI = "${XORG_MIRROR}/individual/xserver/xorg-server-${PV}.tar.bz2 \
	${KDRIVE_COMMON_PATCHES} \
	file://enable-epson.patch;patch=1 \
        file://fix_default_mode.patch;patch=1 \
	file://enable-xcalibrate.patch;patch=1 \
	file://hide-cursor-and-ppm-root.patch;patch=1 \
	file://xcalibrate_coords.patch;patch=1 \
	file://w100.patch;patch=1 \
	file://w100-autofoo.patch;patch=1 \
	file://w100-fix-offscreen-bmp.patch;patch=1 \
	file://w100-new-input-world-order.patch;patch=1 \
	file://linux-keyboard-mediumraw.patch;patch=1 \
	file://xcalibrate-new-input-world-order.patch;patch=1 \
	file://tslib-default-device.patch;patch=1 \
	file://fbdev-evdev.patch;patch=1 \
	file://keyboard-resume-workaround.patch;patch=1 \
	file://xorg-avr32-support.diff;patch=1 \
	file://pkgconfig_fix.patch;patch=1 \
        file://no_xkb.patch;patch=1;pnum=0 \
	file://vm86_masks.patch;patch=1 \
        "

S = "${WORKDIR}/xorg-server-${PV}"

W100_OECONF = "--disable-w100"
W100_OECONF_arm = "--enable-w100"

EXTRA_OECONF += "--enable-builtin-fonts"
