require linux.inc
require linux-openmoko.inc

KERNEL_RELEASE="2.6.34"

SRCREV = "dd1225cc08c3375bf80289ac1965c724881b149a"
OEV = "oe2"
PV = "${KERNEL_RELEASE}-${OEV}+gitr${SRCPV}"

SRC_URI = "\
  git://git.openmoko.org/git/kernel.git;protocol=git;branch=om-gta02-2.6.34 \
# build fix
  file://wm8753-fix-build-with-gcc-4.4.2-which-works-ok-with-.patch \
# patches from Thomas White's gdrm-for-merging branch
  file://0001-DRM-for-platform-devices.patch \
  file://0002-Glamo-DRM-and-KMS-driver.patch \
  file://0003-Work-on-Glamo-core-for-DRM.patch \
  file://0004-JBT6k74-work-for-KMS.patch \
  file://0005-Fix-crash-when-reading-Glamo-registers-via-sysfs.patch \
  file://0006-Fix-dynamic-command-queue-allocation.patch \
  file://0007-Debug-statements-for-testing.patch \
  file://0008-Fix-claim-of-2D-register-resource.patch \
  file://0009-Use-unlocked_ioctl-rather-than-ioctl.patch \
# patches from Radek Polak used in qtmoko
  file://0001-accels.patch.patch \
  file://0002-usbhost.patch.patch \
  file://0003-ar6000_delay.patch.patch \
  file://0004-save_regs.patch.patch \
"

S = "${WORKDIR}/git"

CONFIG_NAME_om-gta02 = "gta02_drm_defconfig"

do_configure_prepend() {
        install -m 644 ./arch/arm/configs/${CONFIG_NAME} ${WORKDIR}/defconfig-oe
}
