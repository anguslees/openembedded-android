DESCRIPTION = "A library to build launchers"
LICENSE = "GPLv3,LGPLv2.1,LGPLv3"

DEFAULT_PREFERENCE = "-1"

DEPENDS = "glib-2.0 wncksync libwnck virtual/libx11 gconf gnome-menus"

SRC_URI = "http://launchpad.net/liblauncher/0.3/0.3.6/+download/liblauncher-0.3.6.tar.gz;name=liblauncher \
           file://0001-liblauncher-udpate-0.3.6-to-0.3.8-which-the-ubuntu-f.patch \
"

SRC_URI[liblauncher.md5sum] = "ba3ea890473f69000d1c843cee471471"
SRC_URI[liblauncher.sha256sum] = "ec3223c6f46cf29d291ccd383527443ba725a0baca65e490307400af0ad30c04"

inherit autotools

S = "${WORKDIR}/${PN}-0.3.6"

