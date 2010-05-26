require xorg-xserver-common.inc

DESCRIPTION = "the X.Org X server"
DEPENDS += "pixman libpciaccess openssl dri2proto glproto xorg-minimal-fonts"
PE = "2"
PR = "${INC_PR}.0"

SRC_URI += "file://sysroot_fix.patch \
            file://dolt-fix-1.7.0.patch \
            file://randr-support-1.7.0.patch \
            file://hack-fbdev-ignore-return-mode.patch \
           "

SRC_URI_append_angstrom = " file://hack-assume-pixman-supports-overlapped-blt.patch"
SRC_URI_append_shr = " file://hack-assume-pixman-supports-overlapped-blt.patch"

do_install_prepend() {
        mkdir -p ${D}/${libdir}/X11/fonts
}

# The NVidia driver requires Xinerama support in the X server. Ion uses it.
XINERAMA = "${@['--disable-xinerama','--enable-xinerama'][bb.data.getVar('MACHINE',d) in ['ion']]}"

EXTRA_OECONF += " --enable-config-hal ${XINERAMA} --disable-kdrive --disable-xephyr --disable-xsdl --disable-xfake --disable-xfbdev --disable-dmx"
EXTRA_OECONF += " --disable-glx-tls --enable-dri2 --disable-unit-tests "

export LDFLAGS += " -ldl "

SRC_URI[archive.md5sum] = "4c63b22cad9ed8ae8b86561f0f92c327"
SRC_URI[archive.sha256sum] = "4b644113cd030fc77615c03c7b6529e063dc1d471ec6a990e6f62eb45a9c2db4"
