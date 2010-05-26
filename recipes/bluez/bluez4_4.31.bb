DESCRIPTION = "Linux Bluetooth Stack Userland V4"
SECTION = "libs"
PRIORITY = "optional"
DEPENDS = "gst-plugins-base alsa-lib virtual/libusb0 dbus-glib"
HOMEPAGE = "http://www.bluez.org"
LICENSE = "GPL"
PR = "r7"

SRC_URI = "\
  http://www.kernel.org/pub/linux/bluetooth/bluez-${PV}.tar.gz \
  file://fix-dfutool-usb-declaration-mismatch.patch \
  file://sbc-thumb.patch \
  file://bluetooth.conf \
#  file://hid2hci_usb_init.patch \
"
S = "${WORKDIR}/bluez-${PV}"

inherit autotools pkgconfig

OE_LT_RPATH_ALLOW = "any"
OE_LT_RPATH_ALLOW[export] = "1"

EXTRA_OECONF = "\
  --enable-gstreamer \
  --enable-alsa \
  --enable-usb \
  --enable-netlink \
  --enable-tools \
  --enable-bccmd \
  --enable-hid2hci \
  --enable-dfutool \
  --enable-hidd \
  --enable-pand \
  --enable-dund \
  --disable-cups \
  --enable-test \
  --enable-manpages \
  --enable-configfiles \
  --enable-initscripts \
  --disable-pcmciarules \
"

do_install_append() {
        install -m 0644 ${S}/audio/audio.conf ${D}/${sysconfdir}/bluetooth/
        install -m 0644 ${S}/network/network.conf ${D}/${sysconfdir}/bluetooth/
        install -m 0644 ${S}/input/input.conf ${D}/${sysconfdir}/bluetooth/
        # at_console doesn't really work with the current state of OE, so punch some more holes so people can actually use BT
        install -m 0644 ${WORKDIR}/bluetooth.conf ${D}/${sysconfdir}/dbus-1/system.d/
}

PACKAGES =+ "gst-plugin-bluez libasound-module-bluez"

FILES_gst-plugin-bluez = "${libdir}/gstreamer-0.10/lib*.so"
FILES_libasound-module-bluez = "${libdir}/alsa-lib/lib*.so"
FILES_${PN} += "${libdir}/bluetooth/plugins/*.so"
FILES_${PN}-dev += "\
  ${libdir}/bluetooth/plugins/*.la \
  ${libdir}/alsa-lib/*.la \
  ${libdir}/gstreamer-0.10/*.la \
"
FILES_${PN}-dbg += "\
  ${libdir}/bluetooth/plugins/.debug \
  ${libdir}/*/.debug \
"

SRC_URI[md5sum] = "53a3347a1c38971bee4c4016b45bcf6d"
SRC_URI[sha256sum] = "f6cfceb541a10849805f853b790bc8343309e7b21078a9c18a7a6a8cf2f94e3b"
