include dbus.inc
PR = "${INC_PR}.1"

SRC_URI = "\
  http://dbus.freedesktop.org/releases/dbus/dbus-${PV}.tar.gz \
  \
  file://bugfix-17754.patch \
  file://tmpdir.patch \
  file://fix-install-daemon.patch \
  file://add-configurable-reply-timeouts.patch \
  file://dbus-1.init \
"            

# This is a development version of dbus that will lead to 1.4.0
DEFAULT_PREFERENCE = "-1"

SRC_URI[md5sum] = "1aab02bdcac4d662d3760896790f0ede"
SRC_URI[sha256sum] = "f58de339c9b1fbea4c9acf555742650443bea5a8851268ca254bcb86bbbf5768"
