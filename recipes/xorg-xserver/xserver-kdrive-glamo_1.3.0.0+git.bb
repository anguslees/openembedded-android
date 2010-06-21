DESCRIPTION = "X server for glamo chip in the Openmoko GTA02 device"
SECTION = "x11/base"
LICENSE = "MIT"
DEPENDS = "compositeproto damageproto fixesproto recordproto resourceproto \
           scrnsaverproto xineramaproto videoproto xextproto xproto \
           libxau libxext libxdmcp libxfont libxrandr tslib virtual/libx11 \
           xtrans libxkbfile libxcalibrate"
DEPENDS += "libxkbfile libxcalibrate"
RDEPENDS_${PN} = "xserver-security-policy"
PROVIDES = "virtual/xserver"

SRCREV = "3f113f5f4dbaf14dee439eac8d510313fff3aafc"
PE = "2"
PV = "1.3.0.0+gitr${SRCPV}"
PR = "r3"

COMPATIBLE_HOST = "arm.*-linux"
COMPATIBLE_MACHINE = 'om-gta02'

FILESPATHPKG =. "xserver-kdrive-1.3.0.0:xserver-kdrive:"

SRC_URI = "git://git.openmoko.org/git/xglamo.git;protocol=git"
S = "${WORKDIR}/git"

inherit autotools pkgconfig

EXTRA_OECONF = "--enable-composite --enable-kdrive \
                --disable-dga --disable-dri --disable-xinerama \
                --disable-xf86misc --disable-xf86vidmode \
                --disable-xorg --disable-xorgcfg \
                --disable-xkb --disable-xnest --disable-xvfb \
                --disable-xevie --disable-xprint --disable-xtrap \
                --disable-dmx \
                --with-default-font-path=built-ins \
                --enable-tslib --enable-xcalibrate \
                ac_cv_file__usr_share_sgml_X11_defs_ent=no"

do_configure_prepend() {
    sed -i -e 's/tslib-0.0/tslib-1.0/' ${S}/configure.ac
}

PACKAGES =+ "xserver-security-policy"
FILES_xserver-security-policy += "${libdir}/xserver/SecurityPolicy"
RRECOMMENDS_${PN} += "xserver-security-policy"

FILES_${PN} = "${bindir}/Xglamo"
FILES_${PN}-dbg = "${bindir}/.debug/Xglamo"

ARM_INSTRUCTION_SET = "arm"
