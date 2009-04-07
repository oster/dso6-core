/**
 * LibreSource
 * Copyright (C) 2004-2008 Artenum SARL / INRIA
 * http://www.libresource.org - contact@artenum.com
 *
 * This file is part of the LibreSource software, 
 * which can be used and distributed under license conditions.
 * The license conditions are provided in the LICENSE.TXT file 
 * at the root path of the packaging that enclose this file. 
 * More information can be found at 
 * - http://dev.libresource.org/home/license
 *
 * Initial authors :
 *
 * Guillaume Bort / INRIA
 * Francois Charoy / Universite Nancy 2
 * Julien Forest / Artenum
 * Claude Godart / Universite Henry Poincare
 * Florent Jouille / INRIA
 * Sebastien Jourdain / INRIA / Artenum
 * Yves Lerumeur / Artenum
 * Pascal Molli / Universite Henry Poincare
 * Gerald Oster / INRIA
 * Mariarosa Penzi / Artenum
 * Gerard Sookahet / Artenum
 * Raphael Tani / INRIA
 *
 * Contributors :
 *
 * Stephane Bagnier / Artenum
 * Amadou Dia / Artenum-IUP Blois
 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 */
package org.libresource.so6.core.compress.test;

import junit.framework.TestCase;

import org.libresource.so6.core.client.DummyClient;
import org.libresource.so6.core.compress.CompressUtil;
import org.libresource.so6.core.engine.DBType;
import org.libresource.so6.core.engine.PatchFile;
import org.libresource.so6.core.engine.util.FileUtils;

import java.io.File;
import java.io.FileWriter;


public class PatchCompressTest extends TestCase {
    private String projectName = "CompressTest";
    private String dir;
    private File[] patchList;
    private String patch1_1 = "<?xml version=\"1.0\"?><patch><name>d3Mx" + "</name><begin>1</begin><end>1</end><comment>Tm8gY29tbWVudC4uLg==" +
        "</comment><command><class>org.libresource.so6.core.command.text.AddTxtFile</class><ticket>1</ticket><from>d3Mx" +
        "</from><time>1083055577039</time><path>ZmlsZTE=" + "</path><attachement>TGluZSAxCkxpbmUgMgpMaW5lIDMKTGluZSA0CkxpbmUgNQoKCgo=" +
        "</attachement></command></patch>";
    String patch2_3 = "<?xml version=\"1.0\"?><patch><name>d3Mx" + "</name><begin>2</begin><end>3</end><comment>Tm8gY29tbWVudC4uLg==" +
        "</comment><command><class>org.libresource.so6.core.command.text.DelBlock</class><ticket>2</ticket><from>d3Mx" +
        "</from><time>1083055577570</time><path>ZmlsZTE=" + "</path><deletepoint>4</deletepoint><linesToRemove><line>TGluZSA0Cg==" +
        "</line></linesToRemove></command><command><class>org.libresource.so6.core.command.text.DelBlock</class><ticket>3</ticket><from>d3Mx" +
        "</from><time>1083055577585</time><path>ZmlsZTE=" + "</path><deletepoint>7</deletepoint><linesToRemove><line>Cg==" +
        "</line></linesToRemove></command></patch>";
    private String patch4_4 = "<?xml version=\"1.0\"?><patch><name>d3Mx" + "</name><begin>4</begin><end>4</end><comment>Tm8gY29tbWVudC4uLg==" +
        "</comment><command><class>org.libresource.so6.core.command.text.DelBlock</class><ticket>4</ticket><from>d3Mx" +
        "</from><time>1083055577695</time><path>ZmlsZTE=" + "</path><deletepoint>4</deletepoint><linesToRemove><line>TGluZSA1Cg==" +
        "</line></linesToRemove></command></patch>";
    private String patch5_6 = "<?xml version=\"1.0\"?><patch><name>d3Mx" + "</name><begin>5</begin><end>6</end><comment>Tm8gY29tbWVudC4uLg==" +
        "</comment><command><class>org.libresource.so6.core.command.text.DelBlock</class><ticket>5</ticket><from>d3Mx" +
        "</from><time>1083055577789</time><path>ZmlsZTE=" + "</path><deletepoint>5</deletepoint><linesToRemove><line>Cg==" +
        "</line></linesToRemove></command><command><class>org.libresource.so6.core.command.text.AddBlock</class><ticket>6</ticket><from>d3Mx" +
        "</from><time>1083055577789</time><path>ZmlsZTE=" +
        "</path><insertpoint>5</insertpoint><originalinsertpoint>5</originalinsertpoint><linesToAdd><line>b2xhCg==" + "</line><line>dHV0dQo=" +
        "</line></linesToAdd></command></patch>";
    private String patch7_27 = "<?xml version=\"1.0\"?><patch><name>d3Mx" + "</name><begin>7</begin><end>27</end><comment>Tm8gY29tbWVudC4uLg==" +
        "</comment><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>7</ticket><from>d3Mx" +
        "</from><time>1083055577914</time><path>YQ==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>8</ticket><from>d3Mx" +
        "</from><time>1083055577929</time><path>YS9i" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>9</ticket><from>d3Mx" +
        "</from><time>1083055577929</time><path>YS9iL2M=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>10</ticket><from>d3Mx" +
        "</from><time>1083055577929</time><path>YS9iL2MvZA==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>11</ticket><from>d3Mx" +
        "</from><time>1083055577929</time><path>YS9iL2MvZC9l" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>12</ticket><from>d3Mx" +
        "</from><time>1083055577929</time><path>YS9iL2MvZC9lL2Y=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>13</ticket><from>d3Mx" +
        "</from><time>1083055577945</time><path>YS9iL2MvZC9lL2YvZw==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>14</ticket><from>d3Mx" +
        "</from><time>1083055577945</time><path>YS9iL2MvZC9lL2YvZy9o" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>15</ticket><from>d3Mx" +
        "</from><time>1083055577945</time><path>YS9iL2Nj" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>16</ticket><from>d3Mx" +
        "</from><time>1083055577945</time><path>YS9iL2NjL2Q=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>17</ticket><from>d3Mx" +
        "</from><time>1083055577960</time><path>YS9iL2NjL2QvZQ==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>18</ticket><from>d3Mx" +
        "</from><time>1083055577960</time><path>YS9iL2NjL2QvZS9m" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>19</ticket><from>d3Mx" +
        "</from><time>1083055577960</time><path>YS9iL2NjL2QvZS9mL2c=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>20</ticket><from>d3Mx" +
        "</from><time>1083055577976</time><path>YS9iL2NjL2QvZS9mL2cvaA==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>21</ticket><from>d3Mx" +
        "</from><time>1083055577976</time><path>YS9iYg==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>22</ticket><from>d3Mx" +
        "</from><time>1083055577976</time><path>YS9iYi9j" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>23</ticket><from>d3Mx" +
        "</from><time>1083055577976</time><path>YS9iYi9jL2Q=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>24</ticket><from>d3Mx" +
        "</from><time>1083055577992</time><path>YS9iYi9jL2QvZQ==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>25</ticket><from>d3Mx" +
        "</from><time>1083055577992</time><path>YS9iYi9jL2QvZS9m" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>26</ticket><from>d3Mx" +
        "</from><time>1083055577992</time><path>YS9iYi9jL2QvZS9mL2c=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.AddDir</class><ticket>27</ticket><from>d3Mx" +
        "</from><time>1083055577992</time><path>YS9iYi9jL2QvZS9mL2cvaA==" + "</path></command></patch>";
    private String patch28_48 = "<?xml version=\"1.0\"?><patch><name>d3Mx" + "</name><begin>28</begin><end>48</end><comment>Tm8gY29tbWVudC4uLg==" +
        "</comment><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>28</ticket><from>d3Mx" +
        "</from><time>1083055578304</time><path>YS9iYi9jL2QvZS9mL2cvaA==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>29</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iYi9jL2QvZS9mL2c=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>30</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iYi9jL2QvZS9m" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>31</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iYi9jL2QvZQ==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>32</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iYi9jL2Q=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>33</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iYi9j" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>34</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iYg==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>35</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2NjL2QvZS9mL2cvaA==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>36</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2NjL2QvZS9mL2c=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>37</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2NjL2QvZS9m" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>38</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2NjL2QvZQ==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>39</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2NjL2Q=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>40</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2Nj" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>41</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2MvZC9lL2YvZy9o" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>42</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2MvZC9lL2YvZw==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>43</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2MvZC9lL2Y=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>44</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2MvZC9l" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>45</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2MvZA==" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>46</ticket><from>d3Mx" +
        "</from><time>1083055578320</time><path>YS9iL2M=" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>47</ticket><from>d3Mx" +
        "</from><time>1083055578335</time><path>YS9i" +
        "</path></command><command><class>org.libresource.so6.core.command.fs.Remove</class><ticket>48</ticket><from>d3Mx" +
        "</from><time>1083055578335</time><path>YQ==" + "</path></command></patch>";

    public PatchCompressTest(String name) {
        super(name);
    }

    public void testCompressHistoryTest() throws Exception {
        File basePatch = File.createTempFile("mergedPatch", null, FileUtils.createTmpDir());
        FileUtils.copy(patchList[0], basePatch);

        for (int i = 1; i < patchList.length; i++) {
            CompressUtil.mergePatch(basePatch, patchList[i], basePatch, new File(dir));
        }

        File dirA = new File(dir, "A");
        File dirB = new File(dir, "B");
        dirA.mkdirs();
        dirB.mkdirs();

        DBType dbType = new DBType(dir + "/dbType", "");

        for (int i = 0; i < patchList.length; i++) {
            PatchFile pf = new PatchFile(patchList[i].getPath());
            pf.patch(dirA.getPath(), dbType);
        }

        PatchFile pf = new PatchFile(basePatch.getPath());
        pf.patch(dirB.getPath(), dbType);
        assertTrue("Wrong compression", FileUtils.compareDir(dirA.getPath(), dirB.getPath()));
        System.out.println(dir);
    }

    public void testDummyCompressHistoryTest() throws Exception {
        DummyClient dc = new DummyClient(dir + "/dummy");
        dc.sendPatch(1, 1, patchList[0].getPath(), true);
        dc.sendPatch(2, 3, patchList[1].getPath(), true);
        dc.sendPatch(4, 4, patchList[2].getPath(), true);
        dc.sendPatch(5, 6, patchList[3].getPath(), true);
        dc.sendPatch(7, 27, patchList[4].getPath(), true);
        dc.sendPatch(28, 48, patchList[5].getPath(), true);
        dc.tagState();
        assertTrue("Invalide patch name", new File(dc.getPatch(1)).getName().equals("1.48"));
    }

    protected void setUp() throws Exception {
        dir = FileUtils.createTmpDir().getPath();
        patchList = new File[6];

        File basePacthDir = new File(dir, "PATCH");
        basePacthDir.mkdirs();
        patchList[0] = new File(basePacthDir, "1.1");
        patchList[1] = new File(basePacthDir, "2.3");
        patchList[2] = new File(basePacthDir, "4.4");
        patchList[3] = new File(basePacthDir, "5.6");
        patchList[4] = new File(basePacthDir, "7.27");
        patchList[5] = new File(basePacthDir, "28.48");

        FileWriter fw = new FileWriter(patchList[0]);
        fw.write(patch1_1);
        fw.close();
        fw = new FileWriter(patchList[1]);
        fw.write(patch2_3);
        fw.close();
        fw = new FileWriter(patchList[2]);
        fw.write(patch4_4);
        fw.close();
        fw = new FileWriter(patchList[3]);
        fw.write(patch5_6);
        fw.close();
        fw = new FileWriter(patchList[4]);
        fw.write(patch7_27);
        fw.close();
        fw = new FileWriter(patchList[5]);
        fw.write(patch28_48);
        fw.close();
    }
}
