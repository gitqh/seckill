package org.gitqh.seckill.web;

import org.gitqh.seckill.dto.Exposer;
import org.gitqh.seckill.dto.SeckillExecution;
import org.gitqh.seckill.dto.SeckillResult;
import org.gitqh.seckill.entity.Seckill;
import org.gitqh.seckill.enums.SeckillEnum;
import org.gitqh.seckill.exception.RepeatKillException;
import org.gitqh.seckill.exception.SeckillCloseException;
import org.gitqh.seckill.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author qh
 * URL:/模块/资源/{ID}/细分
 * @create 2016-06-22-8:06
 */
@Controller
@RequestMapping("/seckill")
public class SeckillController {

    private static Logger logger = LoggerFactory.getLogger(SeckillController.class);

    @Autowired
    private SeckillService seckillService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String getList(Model model) {
        List<Seckill> seckillList = seckillService.getSeckillList();
        model.addAttribute("list", seckillList);
        return "list";
    }

    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String getDetailById(@PathVariable Long seckillId, Model model){
        if (seckillId == null){
            return "redirect:/seckill/list";
        }
        Seckill seckill = seckillService.getById(seckillId);
        if (seckill == null){
            return "forward:/seckill/list";
        }
        model.addAttribute("detail",seckill);
        return "detail";
    }

    //Ajax 返回json，非html responseBody表示返回是json
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<Exposer> expose(@PathVariable Long seckillId){
        SeckillResult<Exposer> result = null;
        try {
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            result = new SeckillResult<Exposer>(true,exposer);
        } catch (Exception e) {
            logger.info(e.getMessage(),e);
            result = new SeckillResult<Exposer>(false,e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/{seckillId}/{md5}/execution",method = RequestMethod.POST,produces = "application/json;charset=UTF-8")
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable Long seckillId, @CookieValue(value = "killPhone",required = false) Long userPhone
            ,@PathVariable String md5){
        SeckillResult<SeckillExecution> result = null;
        if (userPhone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        try {
            SeckillExecution se = seckillService.seckillExecute(seckillId,userPhone,md5);
            return new SeckillResult<SeckillExecution>(true,se);
        } catch (RepeatKillException e) {
            SeckillExecution se = new SeckillExecution(seckillId, SeckillEnum.REPEATE_KILL);
            return new SeckillResult<SeckillExecution>(true,se);
        } catch (SeckillCloseException e){
            SeckillExecution se = new SeckillExecution(seckillId, SeckillEnum.END);
            return new SeckillResult<SeckillExecution>(true,se);
        } catch (Exception e){
            logger.error(e.getMessage(),e);
            SeckillExecution se = new SeckillExecution(seckillId, SeckillEnum.REPEATE_KILL);
            return new SeckillResult<SeckillExecution>(true,se);
        }
    }

    @RequestMapping(value = "/time/now", method = RequestMethod.GET)
    @ResponseBody
    public SeckillResult<Long> time() {
        Date date = new Date();
        return new SeckillResult<Long>(true, date.getTime());
    }

}
