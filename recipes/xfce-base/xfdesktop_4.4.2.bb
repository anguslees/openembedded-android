# xfdesktop OE build file
# Copyright (C) 2004, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see COPYING.MIT)

DESCRIPTION="xfce4 Desktop Background Manager"
SECTION = "x11/base"
DEPENDS = "virtual/libx11 libxfcegui4 libxfce4mcs libxml2 xfce4-panel thunar"
PR = "r2"

inherit xfce

SRC_URI += " file://relocation-and-memleak.patch \
             file://fix-segfault-when-removable-icons-are-disabled.patch"

PACKAGES += "xfdesktop-backdrops ${PN}-mcs-plugins"

FILES_xfdesktop-backdrops="${datadir}/xfce4/backdrops/*"
FILES_${PN}-mcs-plugins += "${libdir}/xfce4/mcs-plugins/*.so"
FILES_${PN} += "${libdir}/xfce4/panel-plugins/*.so \
                ${libdir}/xfce4/panel-plugins/xfce4-menu-plugin \
                ${datadir}/xfce4/panel-plugins/*.desktop"
FILES_${PN}-dbg += "/usr/libexec/xfce4/panel-plugins/.debug"

SRC_URI[md5sum] = "694601234e74903d0fccba064f411e0c"
SRC_URI[sha256sum] = "7e50c13e45b1424924c49dd1e9daa6c16881ee57ac21e4e2c6f20c0eec227cdb"
