require gnutls.inc
LICENSE_${PN}-extra = "GPLv3"

SRC_URI += "\
	file://gnutls-openssl.patch;patch=1 \
	file://gnutls-texinfo-euro.patch;patch=1 \
	file://configure_madness.patch;patch=1 \
	file://gnutls-replace-siginterrupt.patch;patch=1 \
	file://consistent-enum-use.patch;patch=1 \
	"

SRC_URI_append_android = "file://android-header-workaround.patch;patch=1"

PR = "r4"
