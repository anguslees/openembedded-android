"""
BitBake 'Fetch' Android repo implementation

"""

import os

import bb
from bb import data
from bb.fetch import Fetch
from bb.fetch import runfetchcmd

class Repo(Fetch):
    def supports(self, url, ud, d):
        return ud.type in ['repo']

    def localpath(self, url, ud, d):
        # uniqify and sort projects to get better localfile cache hits 
        ud.project = ','.join(sorted(set(ud.parm.get("project", "").split(","))))

        tag = Fetch.srcrev_internal_helper(ud, d)
        if tag is True:
            ud.tag = self.latest_revision(url, ud, d)
        elif tag:
            ud.tag = tag

        if not ud.tag or ud.tag == "master":
            ud.tag = self.latest_revision(url, ud, d)

        ud.localfile = data.expand('repo_%s%s_%s_%s.tar.gz' % (ud.host, ud.path.replace('/', '.'), ud.project.replace('/', '.'), ud.tag), d)
        return os.path.join(data.getVar("DL_DIR", d, True), ud.localfile)

    def go(self, loc, ud, d):
        if Fetch.try_mirror(d, ud.localfile):
            bb.msg.debug(1, bb.msg.domain.Fetch, "%s already exists (or was stashed). Skipping repo checkout." % ud.localpath)
            return

        reposrcname = '%s%s%s' % (ud.host, ud.path.replace('/', '.'), ud.project.replace('/', '.'))

        repodir = os.path.join(data.expand('${REPODIR}', d), reposrcname, 'repo')

        if os.path.exists(os.path.join(repodir, '.repo')):
            os.chdir(repodir)
            runfetchcmd("repo sync --network-only %s" % ud.project.replace(',', ' '), d)
            runfetchcmd("repo forall git checkout %s" % (ud.tag or "master"), d)
        else:
            bb.mkdirhier(repodir)
            os.chdir(repodir)
            args = ["-u", "git://%s%s" % (ud.host, ud.path)]
            if ud.tag:
                args.extend(("-b", ud.tag))
            runfetchcmd("repo init -q %s " % ' '.join(args), d)

        os.chdir(repodir)
        runfetchcmd("repo sync %s" % ud.project.replace(',', ' '), d)

        os.chdir(repodir)
        os.chdir('..')
        bb.msg.note(1, bb.msg.domain.Fetcher, "Creating tarball of repo checkout")
        runfetchcmd("tar -czf %s %s" % (ud.localpath, os.path.join(os.path.basename(repodir), "*")), d)

    def supports_srcrev(self):
        return False
