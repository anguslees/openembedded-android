require dbus.inc

PR = "${INC_PR}.0"
DEFAULT_PREFERENCE = "-1"

inherit native

EXTRA_OECONF_X = "--without-x"

DEPENDS = "glib-2.0-native libxml2-native expat-native"
FILESDIR = "${@os.path.dirname(bb.data.getVar('FILE',d,1))}/dbus-${PV}"

SRC_URI = "\
  http://freedesktop.org/software/dbus/releases/dbus/dbus-${PV}.tar.gz \
  file://cross.patch \
  file://tmpdir.patch \
  file://fedora-compile-fix.patch \
  file://dbus-1.init \
"

do_stage() {
	oe_runmake install
	autotools_stage_all

	# for dbus-glib-native introspection generation
	install -d ${STAGING_DATADIR}/dbus
	install -m 0644 bus/session.conf ${STAGING_DATADIR}/dbus/session.conf
}

do_install() {
	:
}

SRC_URI[md5sum] = "ce328423db5743fe9006d39113b5324d"
SRC_URI[sha256sum] = "4be82f4ef8f4657f997a44b3daddba3f95dbb6518487509767d803dd72bf529e"
