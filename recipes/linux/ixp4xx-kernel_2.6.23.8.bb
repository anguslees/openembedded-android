# Kernel for IXP4xx
#
# This bitbake file pulls patches and the defconfig straight
# from the NSLU2-Linux SVN repository. Information about this
# repository can be found at:
# http://trac.nslu2-linux.org/kernel/
#
# The revision that is pulled from SVN is specified below
IXP4XX_KERNEL_SVN_REV = "925"
#
# The directory containing the patches to be applied is
# specified below
IXP4XX_KERNEL_PATCH_DIR = "2.6.23"
#
# Increment the number below (i.e. the digits after PR) when
# the changes in SVN between revisions include changes in the
# patches applied to the kernel, rather than simply defconfig
# changes
PR = "r0.${IXP4XX_KERNEL_SVN_REV}"

require ixp4xx-kernel.inc
require ixp4xx-kernel-svnpatch.inc

SRC_URI += "file://defconfig"
SRC_URI[kernel.md5sum] = "2a8895d7e91f4ab7b3c582d8840fe685"
SRC_URI[kernel.sha256sum] = "64941e9918fbed47a931df0bfeeb7d6953dabcb1abb70db0f79e03e505e7e2ea"
