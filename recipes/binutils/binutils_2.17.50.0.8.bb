require binutils.inc
PR = "${INC_PR}.0"

SRC_URI = \
    "${KERNELORG_MIRROR}/pub/linux/devel/binutils/binutils-${PV}.tar.bz2 \
     file://binutils-2.16.91.0.6-objcopy-rename-errorcode.patch \
     file://binutils-uclibc-100-uclibc-conf.patch \
     file://binutils-configure-texinfo-version.patch \
     file://110-arm-eabi-conf.patch \
     file://binutils-uclibc-300-001_ld_makefile_patch.patch \
     file://binutils-uclibc-300-006_better_file_error.patch \
     file://binutils-uclibc-300-012_check_ldrunpath_length.patch \
     "

SRC_URI[md5sum] = "1441fe6fa44b344d0575cb66d3f89252"
SRC_URI[sha256sum] = "016b0faa1bbe20c13a4b5f495a5a4071349f6385012b767c89bb908452faecf2"
