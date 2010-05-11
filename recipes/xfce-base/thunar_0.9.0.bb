DESCRIPTION = "File manager for the Xfce Desktop Environment"
DEPENDS = "libxfcegui4 exo dbus-glib libexif xfce4-panel libice libsm gamin"
RRECOMMENDS = "shared-mime-info"

inherit xfce pkgconfig

SRC_URI = "http://www.us.xfce.org/archive/xfce-4.4.2/src/Thunar-${PV}.tar.bz2"
PR = "r4"

S = "${WORKDIR}/Thunar-${PV}/"

FILES_${PN} += "${libdir}/thunarx-1/*.so \
                ${datadir}/dbus-1 \
                ${datadir}/thumbnailers \
                ${datadir}/Thunar \
                ${datadir}/xfce4"
FILES_${PN}-dbg += "${libdir}/thunarx-1/.debug/ ${libexecdir}/xfce4/panel-plugins/.debug/"

SRC_URI[md5sum] = "0fc5008858661c0abd0399acbe30ef28"
SRC_URI[sha256sum] = "26c833b0bdf3281c61e03f1fb985feced88cf4a5cebce7bd055f05e025460037"
