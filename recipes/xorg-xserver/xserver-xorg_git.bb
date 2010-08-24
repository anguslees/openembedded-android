# Assign it here, since the hal->udev transition happens post 1.7 in angstrom
DISTRO_XORG_CONFIG_MANAGER_angstrom = "udev"

require xorg-xserver-common.inc

DESCRIPTION = "the X.Org X server"
DEPENDS += "pixman libpciaccess openssl dri2proto glproto xorg-minimal-fonts font-util-native"
PV = "1.8.99.906"
PR = "${INC_PR}.4"
PR_append = "+gitr${SRCPV}"
PE = "2"

DEFAULT_PREFERENCE = "-1"

SRCREV = "71af1f71c0492c365707c6b3810f94642ff39352"
SRC_URI = "git://anongit.freedesktop.org/xorg/xserver;protocol=git;branch=master \
           file://dolt-fix.patch \
           file://randr-support.patch \
	   file://hack-fbdev-ignore-return-mode.patch \
           "

SRC_URI_append_angstrom = " file://hack-assume-pixman-supports-overlapped-blt.patch"
SRC_URI_append_shr = " file://hack-assume-pixman-supports-overlapped-blt.patch"

S = "${WORKDIR}/git"

do_install_prepend() {
        mkdir -p ${D}/${libdir}/X11/fonts
}

# The NVidia driver requires Xinerama support in the X server. Ion uses it.
PACKAGE_ARCH_ion = "${MACHINE_ARCH}"
XINERAMA = "${@['--disable-xinerama','--enable-xinerama'][bb.data.getVar('MACHINE',d) in ['ion']]}"

EXTRA_OECONF += " ${CONFIG_MANAGER_OPTION} ${XINERAMA} --disable-kdrive --disable-xephyr --disable-xsdl --disable-xfake --disable-xfbdev --disable-dmx"
EXTRA_OECONF += " --enable-dri2 --disable-unit-tests "

export LDFLAGS += " -ldl "
