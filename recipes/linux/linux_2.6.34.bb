require linux.inc

PR = "r1"

module_autoload_ohci-hcd_omap5912osk = "ohci-hcd"

# Mark archs/machines that this kernel supports
DEFAULT_PREFERENCE = "-1"
DEFAULT_PREFERENCE_qemuarm = "1"
DEFAULT_PREFERENCE_qemuppc = "1"
DEFAULT_PREFERENCE_qemush4 = "1"
DEFAULT_PREFERENCE_qemumips = "1"
DEFAULT_PREFERENCE_qemumipsel = "1"
DEFAULT_PREFERENCE_qemux86 = "1"
DEFAULT_PREFERENCE_lite5200 = "1"
DEFAULT_PREFERENCE_omap5912osk = "1"
DEFAULT_PREFERENCE_tqm8540 = "1"

SRC_URI = "${KERNELORG_MIRROR}/pub/linux/kernel/v2.6/${P}.tar.bz2;name=kernel \
           file://ARM-Add-support-for-LZMA-compressed-kernel-images.patch;status=pending \
           file://use-noclone-attribute-for-naked.patch;status=pending \
           file://defconfig"

SRC_URI_append_c7x0 = " file://fix-corgi-card-detection.patch;status=pending "

SRC_URI[kernel.md5sum] = "10eebcb0178fb4540e2165bfd7efc7ad"
SRC_URI[kernel.sha256sum] = "fa395fec7de633df1cb85b6248b8f35af98380ed128a8bc465fb48bc4d252633"
