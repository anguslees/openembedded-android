# Kernel for IXP4xx
#
# This bitbake file pulls patches and the defconfig straight
# from the NSLU2-Linux SVN repository. Information about this
# repository can be found at:
# http://trac.nslu2-linux.org/kernel/
#
# The revision that is pulled from SVN is specified below
IXP4XX_KERNEL_SVN_REV = "915"
#
# The directory containing the patches to be applied is
# specified below
IXP4XX_KERNEL_PATCH_DIR = "2.6.21"
#
# Increment the number below (i.e. the digits after PR) when
# the changes in SVN between revisions include changes in the
# patches applied to the kernel, rather than simply defconfig
# changes
PR = "r2.${IXP4XX_KERNEL_SVN_REV}"

require ixp4xx-kernel.inc
require ixp4xx-kernel-svnpatch.inc

SRC_URI += "file://defconfig"

SRC_URI[kernel.md5sum] = "8f27a934b47b199bd0a1abe57a5005f2"
SRC_URI[kernel.sha256sum] = "73742a6227ec646106173f7dcc0ec6260f2fee65ff16f78b1f63ebb080c0fff1"
