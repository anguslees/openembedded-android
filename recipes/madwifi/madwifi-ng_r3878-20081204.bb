# Bitbake recipe for the madwifi-ng driver

# This recipe needs testing by other than ixp4xx distros before
# it can become the default:
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_slugos = "1"

# Disable stripping of kernel modules, since this action strips too
# much out, and the resulting module won't load.
PACKAGE_STRIP = "no"

require madwifi-ng_r.inc

SRCNAME = "madwifi-trunk"

# PR set after the include, to override what's set in the included file.
PR = "r1"

# It's not clear that we even need the wackelf patches any longer; certainly
# they are not required for ixp4xx builds.  This needs testing on pxa270.
WACKELF_SRC_URI_ixp4xx          = ""
WACKELF_SRC_URI_compulab-pxa270 = ""

# This works for EABI as well as the original OABI IXP4xx.
EXTRA_OEMAKE = "V=1 KERNELPATH=${STAGING_KERNEL_DIR} KERNELRELEASE=${KERNEL_VERSION} TOOLPREFIX=${TARGET_PREFIX}"

# We really must clear out LDFLAGS to get this to link.
do_compile() {
	unset LDFLAGS
	oe_runmake all
}

SRC_URI[md5sum] = "bf12d0f9f306dad91bf7530586712dee"
SRC_URI[sha256sum] = "3b8ea233bee05edc346c66577b6beb5ccc81db9d9440982624d7b39aecc79c77"
