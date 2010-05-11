# ALSA OE build file for the AMD Geode 5535/5536
# Copyright (C) 2005-2006, Advanced Micro Devices, Inc.  All Rights Reserved
# Released under the MIT license (see packages/COPYING)

DESCRIPTION = "Linux 2.6.11 ALSA Audio Driver for the AMD Geode 5535/5536 companion chip"
HOMEPAGE = "http://www.amd.com/embedded"

PR = "r0"
AMD_DRIVER_VERSION = "1.00.0500"
AMD_DRIVER_LABEL = "Audio_LinuxALSA_5536_${AMD_DRIVER_VERSION}"

require geode-modules.inc
SRC_URI[md5sum] = "8bd2a92fb94faa1b4fc43865d40bd988"
SRC_URI[sha256sum] = "a317cd11719659892bdae6ec54ee2e37549d0df7d5bdce02036963acc2ad1486"
#CHECKSUMS.INI MISMATCH: I've got this instead:
#SRC_URI[md5sum] = "254e7227c685ec04867024f6d5963dd6"
#SRC_URI[sha256sum] = "7ce7f7e75a55a2e25d33887a0b4f5ac245536caf886a08353a0b969c9ce35151"
