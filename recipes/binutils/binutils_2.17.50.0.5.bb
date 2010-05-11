require binutils.inc

PR = "r3"

SRC_URI = \
    "${KERNELORG_MIRROR}/pub/linux/devel/binutils/binutils-${PV}.tar.bz2 \
     file://binutils-2.16.91.0.6-objcopy-rename-errorcode.patch;patch=1 \
     file://binutils-uclibc-100-uclibc-conf.patch;patch=1 \
     file://binutils-configure-texinfo-version.patch;patch=1 \
     file://110-arm-eabi-conf.patch;patch=1 \
     file://binutils-uclibc-300-001_ld_makefile_patch.patch;patch=1 \
     file://binutils-uclibc-300-006_better_file_error.patch;patch=1 \
     file://binutils-uclibc-300-012_check_ldrunpath_length.patch;patch=1 \
     "

SRC_URI[md5sum] = "00eccd47e19a9f24410a137a849aa3fc"
SRC_URI[sha256sum] = "bbfa06ee0173c5e9ae65ff14ba29502ddf4e355ac3419f88e3346299cfaf4e19"
