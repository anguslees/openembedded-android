DESCRIPTION = "GStreamer plugin for using pulse audio as sink and source"
HOMEPAGE = "http://0pointer.de/lennart/projects/gst-pulse/"
LICENSE = "GPL"
DEPENDS = "gstreamer pulseaudio gst-plugins-base"
PR = "r1"

SRC_URI = "http://0pointer.de/lennart/projects/gst-pulse/gst-pulse-${PV}.tar.gz \
	file://dont-overload-pulseaudio.patch"
S = "${WORKDIR}/gst-pulse-${PV}"

inherit autotools

EXTRA_OECONF = "--disable-lynx"
GST_LIBV = 0.10

do_install() {
	install -d ${D}${libdir}/gstreamer-${GST_LIBV}/
	install -m 0755 src/.libs/libgstpulse.so ${D}${libdir}/gstreamer-${GST_LIBV}
}

export GST_MODDIR=${libdir}/gstreamer-0.10

FILES_${PN} = "${libdir}/gstreamer-0.10/libgstpulse.so"
FILES_${PN}-dev = "\
  ${libdir}/gstreamer-0.10/libgstpulse.a \
  ${libdir}/gstreamer-0.10/libgstpulse.la \
  ${libdir}/gstreamer-0.10/libgstpulse.so*"
FILES_${PN}-dbg += "${libdir}/gstreamer-0.10/.debug"


SRC_URI[md5sum] = "166164eb07eacd1d70b965731eb6cbdb"
SRC_URI[sha256sum] = "b9f1bb98917d9879d46b6a959ce9068471908763f2ca98e480837485e290b106"
