DESCRIPTION = "Free42 RPN Calculator"
HOMEPAGE = "http://free42.sf.net"
SECTION = "openmoko/applications"
DEPENDS = "gtk+"
PR = "r1"

SRC_URI = "${SOURCEFORGE_MIRROR}/free42/free42.tgz;name=archive \
           http://sense.net/zc/free42/42c_skins.tgz;name=skins \
           file://free42-bcd-gtk-only.patch \
           file://free42-vga-skin.patch \
           file://free42-dont-declare-sincos.patch \
           file://free42dec.desktop"

S = "${WORKDIR}/free42"

TARGET_CXXFLAGS_append = " -fsigned-char"
TARGET_CXXFLAGS_append += "-DVERSION=\\"${PV}\\" -DBCD_MATH"
TARGET_CXXFLAGS_append += "-I${STAGING_INCDIR}/gtk-2.0"
TARGET_CXXFLAGS_append += "-I${STAGING_LIBDIR}/gtk-2.0/include"
TARGET_CXXFLAGS_append += "-I${STAGING_INCDIR}/cairo"
TARGET_CXXFLAGS_append += "-I${STAGING_INCDIR}/glib-2.0"
TARGET_CXXFLAGS_append += "-I${STAGING_INCDIR}/pango-1.0"
TARGET_CXXFLAGS_append += "-I${STAGING_INCDIR}/atk-1.0"

do_compile() {
        cp -fv ${S}/common/* ${S}/gtk
        cp -fv ${S}/ppcskins/Ehrling42ssv.* ${S}/skins/ 
        cp ${WORKDIR}/42ct.* ${S}/skins/
        cd ${S}/gtk/
        ${BUILD_CXX} -o skin2cc skin2cc.cc ${BUILD_CXXFLAGS}
        ${BUILD_CXX} -o keymap2cc keymap2cc.cc ${BUILD_CXXFLAGS}
        oe_runmake
}

do_install() {
        install -d ${D}${bindir}
        install -m 0755 ${S}/gtk/free42dec ${D}${bindir}/free42dec
        install -d ${D}${datadir}/applications
        install -m 0644 ${WORKDIR}/free42dec.desktop ${D}${datadir}/applications/
        install -d ${D}${datadir}/pixmaps
        install -m 0644 ${S}/gtk/icon.xpm ${D}${datadir}/pixmaps/free42.xpm
}


SRC_URI[archive.md5sum] = "1fc21e04b3b77150846027dc7cb5210d"
SRC_URI[archive.sha256sum] = "9d348a9d2d5bf8eabc7e7bb6f0907e611403e4abe6dc1e11b1b2ad1118b3088a"

SRC_URI[skins.md5sum] = "24b0e5bbfe21ea9c5a5c589bcc79c0f2"
SRC_URI[skins.sha256sum] = "ab877846c6caaff32efbf5be6fc5c63b7dec97a2e78c57c525da7705d2052bfc"
